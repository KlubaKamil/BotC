package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.service.CharacterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/character")
@Slf4j
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacter(@PathVariable long id){
        log.info("Getting a character: {}", id);
        CharacterDto characterDto = characterService.getCharacter(id);
        log.info("Finished getting a character");
        return ResponseEntity.ok(characterDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterDto>> getAllCharacters(){
        log.info("Getting all characters.");
        List<CharacterDto> characterDtos = characterService.getAllCharacters();
        log.info("Finished getting all characters");
        return ResponseEntity.ok(characterDtos);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> addCharacter(@Valid @RequestBody CharacterDto characterDto){
        log.info("Creating new character: {}", characterDto);
        long id = characterService.createCharacter(characterDto);
        log.info("Finished creating new character.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editCharacter(@Valid @RequestBody CharacterDto characterDto){
        log.info("Editing an existing character: {}", characterDto);
        long id = characterService.editCharacter(characterDto);
        log.info("Finished editing an existing character.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCharacter(@PathVariable("id") long id){
        log.info("Deleting a character: {}", id);
        characterService.deleteCharacter(id);
        log.info("Finished deleting a character");
    }
}
