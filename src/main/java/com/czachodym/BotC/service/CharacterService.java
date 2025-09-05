package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.character.CharacterInScriptDetails;
import com.czachodym.BotC.dto.headers.CharacterHeader;
import com.czachodym.BotC.model.Alignment;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

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
    private final Validators validators;

    public CharacterDto getCharacter(long id, long groupId){
        log.info("Checking if character exists.");
        Character character = validators.throwIfNotFoundByIdAndGroupId(id, groupId, characterRepository);
        log.info("Character found, getting details.");
        CharacterDetails characterDetails = characterRepository.findCharacterDetailsById(id).orElseThrow();
        List<CharacterInScriptDetails> characterInScriptsDetails = characterRepository.findCharacterInScriptDetailsById(id);
        characterDetails = characterDetails.toBuilder()
                .characterInScriptsDetails(characterInScriptsDetails)
                .build();
        return dtoMapper.mapCharacter(character, characterDetails);
    }

    public List<CharacterDto> getAllCharacters(long groupId){
        log.info("Getting all characters");
        List<Character> characters = characterRepository.findByGroups_Id(groupId);
        log.info("Characters found.");
        return dtoMapper.mapCharacterList(characters);
    }

    public List<CharacterHeader> getAllCharacterHeaders(long groupId){
        log.info("Getting all character headers");
        List<CharacterHeader> characterHeaders = characterRepository.findAllCharacterHeaders(groupId);
        log.info("Headers found.");
        return characterHeaders;
    }
    public long createCharacter(long groupId, CharacterDto characterDto){
        String name = characterDto.name();
        log.info("Checking if character exists.");
        validators.throwIfExistsByName(name, characterRepository);
        Character character = buildCharacter(groupId, characterDto);
        log.info("Saving new character.");
        character = characterRepository.save(character);
        long id = character.getId();
        log.info("Character saved: {}.", id);

        return id;
    }

    public long editCharacter(long groupId, CharacterDto characterDto){
        long id = characterDto.id();
        String name = characterDto.name();
        log.info("Checking if character exists.");
        Character character = validators.throwIfNotFoundByIdAndGroupId(id, groupId, characterRepository);
        if(!character.getName().equals(characterDto.name())) {
            validators.throwIfExistsByName(name, characterRepository);
        }
        log.info("Character found, updating.");
        Character updatedCharacter = buildCharacter(groupId, characterDto, character);
        characterRepository.save(updatedCharacter);
        log.info("Character updated. Id: {}", id);
        boolean hasImage = characterDto.imageUploaded();
        if(!hasImage){
            log.info("Trying to delete image.");
//            deleteImage(characterDto.name());
            //TODO
        }
        return id;
    }

    @Transactional
    public void deleteCharacter(long id, long groupId){
        log.info("Deleting a character.");
        boolean exists = characterRepository.existsById(id);
        characterRepository.deleteById(id);
        boolean deleted = exists & !characterRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    public boolean uploadImage(long id, long groupId, MultipartFile image) {
        try{
            log.info("Checking if character exists.");
            Character character = validators.throwIfNotFoundByIdAndGroupId(id, groupId, characterRepository);
            log.info("Character found, saving.");
            String filename = character.getId() + ".png";
            Path filePath = root.resolve(groupId + "/orig/" + filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Thumbnails.of(image.getInputStream())
                    .size(100, 100)
                    .toFile(root.resolve(groupId + "/100/" + filename)
                            .toFile());
            Thumbnails.of(image.getInputStream())
                    .size(30, 30)
                    .toFile(root.resolve(groupId + "/30/" + filename)
                            .toFile());
            character.setImageUploaded(true);
            characterRepository.save(character);
            log.info("Image saved.");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Resource getImage(long groupId, String size, long characterId){
        Path filePath = root.resolve(Paths.get(groupId + "/" + size + "/" + characterId + ".png"));
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void deleteImage(long groupId, String characterName) {
        Path filePathOrig = root.resolve(Paths.get(groupId + "/orig/" + characterName + ".png"));
        Path filePath100 = root.resolve(Paths.get(groupId + "/100/" + characterName + ".png"));
        Path filePath30 = root.resolve(Paths.get(groupId + "/30/" + characterName + ".png"));
        try {
            Files.delete(filePathOrig);
            Files.delete(filePath100);
            Files.delete(filePath30);
            log.info("Image deleted.");
        } catch (IOException e) {
            log.info("No image found.");
        }
    }

    public String getMessage(long id, NotificationMode notificationMode){
        Character character = validators.throwIfNotFoundByIdAndGroupId(id, 0, characterRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nową postać!" : "Edytowano postać!";
        String name = character.getName();
        int maxStartNumber = character.getMaxStartNumber();
        Alignment alignment = character.getAlignment();
        String description = validators.getIfNotNull(character.getDescription(), "-");
        String linkToWiki = validators.getIfNotNull(character.getLinkToWiki(), "-");
        String tips = validators.getIfNotNull(character.getTips(), "-");
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

    private Character buildCharacter(long groupId, CharacterDto characterDto){
        return buildCharacter(groupId, characterDto, new Character());
    }

    private Character buildCharacter(long groupId, CharacterDto characterDto, Character character){
        log.info("Validating a characterDto.");

        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<Group> groups = character.getGroups();
        groups.add(group);
        log.info("Group available.");

        log.info("Validation successful, building character.");
        return character.toBuilder()
                .groups(groups)
                .name(characterDto.name())
                .maxStartNumber(characterDto.maxStartNumber())
                .alignment(characterDto.alignment())
                .description(characterDto.description())
                .linkToWiki(characterDto.linkToWiki())
                .tips(characterDto.tips())
                .imageUploaded(characterDto.imageUploaded())
                .build();
    }
}
