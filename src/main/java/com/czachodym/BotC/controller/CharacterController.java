package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.service.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Long>> addCharacter(@RequestBody CharacterDto characterDto){
        log.info("Creating new character: {}", characterDto);
        long id = characterService.createCharacter(characterDto);
        log.info("Finished creating new character.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editCharacter(@RequestBody CharacterDto characterDto){
        log.info("Editing an existing character: {}", characterDto);
        long id = characterService.editCharacter(characterDto);
        log.info("Finished editing an existing character.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCharacter(@PathVariable("id") long id){
        log.info("Deleting a character: {}", id);
        boolean deleted = characterService.deleteCharacter(id);
        log.info("Finished deleting a character");
        return ResponseEntity.ok(Map.of("deleted", deleted));
    }
}
