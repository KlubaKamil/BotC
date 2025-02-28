package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.throwIfExistsByName;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final DtoMapper dtoMapper;

    public CharacterDto getCharacter(long id){
        log.info("Checking if character exists.");
        Character character = throwIfNotFoundById(id, characterRepository);
        log.info("Character found.");
        return dtoMapper.mapCharacter(character);
    }

    public List<CharacterDto> getAllCharacters(){
        log.info("Getting all characters");
        List<Character> characters = characterRepository.findAll();
        log.info("Characters found.");
        return dtoMapper.mapCharacterList(characters);
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
        throwIfExistsByName(name, characterRepository);
        log.info("Character found, updating.");
        Character updatedCharacter = buildCharacter(characterDto, character);
        characterRepository.save(updatedCharacter);
        log.info("Character updated. Id: {}", id);

        return id;
    }

    @Transactional
    public boolean deleteCharacter(long id){
        log.info("Deleting a character.");
        boolean exists = characterRepository.existsById(id);
        characterRepository.deleteById(id);
        boolean deleted = exists & !characterRepository.existsById(id);
        log.info("Deleted: {}", deleted);

        return deleted;
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
                .alignment(characterDto.alignment())
                .description(characterDto.description())
                .linkToWiki(characterDto.linkToWiki())
                .build();
    }
}
