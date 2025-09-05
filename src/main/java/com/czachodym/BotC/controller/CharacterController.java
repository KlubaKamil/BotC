package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.headers.CharacterHeader;
import com.czachodym.BotC.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/character/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacter(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a character, id: {}, groupId: {}", id, groupId);
        CharacterDto characterDto = characterService.getCharacter(id, groupId);
        log.info("Finished getting a character");
        return ResponseEntity.ok(characterDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterDto>> getAllCharacters(@PathVariable long groupId){
        log.info("Getting all characters for groupId: {}.", groupId);
        List<CharacterDto> characterDtos = characterService.getAllCharacters(groupId);
        log.info("Finished getting all characters");
        return ResponseEntity.ok(characterDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<CharacterHeader>> getAllCharacterHeaders(@PathVariable long groupId){
        log.info("Getting all character headers for groupId: {}.", groupId);
        List<CharacterHeader> characterHeaders = characterService.getAllCharacterHeaders(groupId);
        log.info("Finished getting all character headers");
        return ResponseEntity.ok(characterHeaders);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> addCharacter(@PathVariable long groupId, @Valid @RequestBody CharacterDto characterDto){
        log.info("Creating new character, groupId: {}, dto: {}", groupId, characterDto);
        long id = characterService.createCharacter(groupId, characterDto);
        log.info("Finished creating new character.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editCharacter(@PathVariable long groupId, @Valid @RequestBody CharacterDto characterDto){
        log.info("Editing an existing character: groupId: {}, dto: {}", groupId, characterDto);
        long id = characterService.editCharacter(groupId, characterDto);
        log.info("Finished editing an existing character.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCharacter(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a character: id: {}, groupId: {}", id, groupId);
        characterService.deleteCharacter(id, groupId);
        log.info("Finished deleting a character");
    }

    @GetMapping("/image/{size}/{characterId}")
    public ResponseEntity<Resource> getImage(@PathVariable long groupId, @PathVariable String size, @PathVariable long characterId){
        Resource resource = characterService.getImage(groupId, size, characterId);
        return resource == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
    }

    @PostMapping(path = "/{id}/image")
    private ResponseEntity<Map<String, Long>> uploadImagePost(@PathVariable long id, @PathVariable long groupId, @RequestParam("image") MultipartFile image) {
        return uploadImage(id, groupId, image, OK);
    }

    @PutMapping(path = "/{id}/image")
    private ResponseEntity<Map<String, Long>> uploadImagePut(@PathVariable long id, @PathVariable long groupId, @RequestParam("image") MultipartFile image){
        return uploadImage(id, groupId, image, CREATED);
    }

    private ResponseEntity<Map<String, Long>> uploadImage(long id, long groupId, MultipartFile image, HttpStatus returnStatus) {
        log.info("Uploading an image, id: {}, groupId: {}", id, groupId);
        boolean success = characterService.uploadImage(id, groupId, image);
        log.info("Finished uploading an image.");
        return success ? ResponseEntity.status(returnStatus).body(Map.of("id", id)) :
                ResponseEntity.internalServerError().build();
    }
}
