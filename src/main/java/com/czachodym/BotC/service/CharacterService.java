package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.character.CharacterInScriptDetails;
import com.czachodym.BotC.dto.headers.CharacterHeader;
import com.czachodym.BotC.model.Alignment;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;

    private final String CHARACTER_IMAGES_DIR = "character_images";
    private final Path root = Paths.get(CHARACTER_IMAGES_DIR);
    private final CharacterRepository characterRepository;
    private final DtoMapper dtoMapper;

    public CharacterDto getCharacter(long id){
        log.info("Checking if character exists.");
        Character character = throwIfNotFoundById(id, characterRepository);
        log.info("Character found, getting details.");
        CharacterDetails characterDetails = characterRepository.findCharacterDetailsById(id).orElseThrow();
        List<CharacterInScriptDetails> characterInScriptsDetails = characterRepository.findCharacterInScriptDetailsById(id);
        characterDetails = characterDetails.toBuilder()
                .characterInScriptsDetails(characterInScriptsDetails)
                .build();
        return dtoMapper.mapCharacter(character, characterDetails);
    }

    public List<CharacterDto> getAllCharacters(){
        log.info("Getting all characters");
        List<Character> characters = characterRepository.findAll();
        log.info("Characters found.");
        return dtoMapper.mapCharacterList(characters);
    }

    public List<CharacterHeader> getAllCharacterHeaders(){
        log.info("Getting all character headers");
        List<CharacterHeader> characterHeaders = characterRepository.findAllCharacterHeaders();
        log.info("Headers found.");
        return characterHeaders;
    }

    public Resource getCharacterImage(String size, String characterName){
        Path filePath = root.resolve(Paths.get(size + "/" + characterName + ".png"));
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public long createCharacter(CharacterDto characterDto){
        String name = characterDto.name();
        log.info("Checking if character exists.");
        throwIfExistsByName(name, characterRepository);
        Character character = buildCharacter(characterDto);
        log.info("Saving new character.");
        character = characterRepository.save(character);
        long id = character.getId();
        log.info("Character saved: {}.", id);

        return id;
    }

    public long editCharacter(CharacterDto characterDto){
        long id = characterDto.id();
        String name = characterDto.name();
        log.info("Checking if character exists.");
        Character character = throwIfNotFoundById(id, characterRepository);
        if(!character.getName().equals(characterDto.name())) {
            throwIfExistsByName(name, characterRepository);
        }
        log.info("Character found, updating.");
        Character updatedCharacter = buildCharacter(characterDto, character);
        characterRepository.save(updatedCharacter);
        log.info("Character updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deleteCharacter(long id){
        log.info("Deleting a character.");
        boolean exists = characterRepository.existsById(id);
        characterRepository.deleteById(id);
        boolean deleted = exists & !characterRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    public String getMessage(long id, NotificationMode notificationMode){
        Character character = throwIfNotFoundById(id, characterRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nową postać!" : "Edytowano postać!";
        String name = character.getName();
        int maxStartNumber = character.getMaxStartNumber();
        Alignment alignment = character.getAlignment();
        String description = getIfNull(character.getDescription(), "-");
        String linkToWiki = getIfNull(character.getLinkToWiki(), "-");
        String tips = getIfNull(character.getTips(), "-");
        return """
                %s
                Id: %d
                Nazwa: %s
                Max liczba na start: %d
                Przynależność: %s
                Opis: %s
                Link do wiki: %s
                Wskazówki: %s
                Kliknij i zobacz: %s/characters/%d
                """.formatted(modeMessage, id, name, maxStartNumber, alignment, description, linkToWiki, tips,
                    FRONTEND_URL, id);
    }

    private Character buildCharacter(CharacterDto characterDto){
        return buildCharacter(characterDto, Character.builder());
    }

    private Character buildCharacter(CharacterDto characterDto, Character character){
        Character.CharacterBuilder<?,?> characterBuilder = character.toBuilder()
                .id(character.getId());
        return buildCharacter(characterDto, characterBuilder);
    }

    private Character buildCharacter(CharacterDto characterDto, Character.CharacterBuilder<?,?> builder){
        return builder
                .name(characterDto.name())
                .maxStartNumber(characterDto.maxStartNumber())
                .alignment(characterDto.alignment())
                .description(characterDto.description())
                .linkToWiki(characterDto.linkToWiki())
                .tips(characterDto.tips())
                .build();
    }
}
