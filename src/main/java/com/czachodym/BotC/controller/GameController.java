package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;



    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable long id){
        log.info("Getting a game: {}", id);
        GameDto gameDto = gameService.getGame(id);
        log.info("Finished getting a game");
        return ResponseEntity.ok(gameDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GameDto>> getAllGames(){
        log.info("Getting all games.");
        List<GameDto> gameDtos = gameService.getAllGames();
        log.info("Finished getting all games");
        return ResponseEntity.ok(gameDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<GameHeader>> getAllGameHeaders(){
        log.info("Getting all game headers.");
        List<GameHeader> gameDtos = gameService.getAllGameHeaders();
        log.info("Finished getting all game headers");
        return ResponseEntity.ok(gameDtos);
    }
    
    @PutMapping
    public ResponseEntity<Map<String, Long>> addGame(@RequestBody GameDto gameDto){
        log.info("Creating new game: {}", gameDto);
        long id = gameService.createGame(gameDto);
        log.info("Finished creating new game.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editGame(@RequestBody GameDto gameDto){
        log.info("Editing an existing game: {}", gameDto);
        long id = gameService.editGame(gameDto);
        log.info("Finished editing an existing game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteGame(@PathVariable("id") long id){
        log.info("Deleting a game: {}", id);
        gameService.deleteGame(id);
        log.info("Finished deleting a game");
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Map<String, Long>> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        log.info("Uploading an image.");
        String url = gameService.uploadImage(id, image);
        log.info("Finished uploading an image.");
        return ResponseEntity.status(OK).body(Map.of("id", id));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Map<String, Long>> uploadImage2(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        log.info("Uploading an image.");
        String url = gameService.uploadImage(id, image);
        log.info("Finished uploading an image.");
        return url == null ? ResponseEntity.internalServerError().build() :
                ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        log.info("Getting an image.");
        Resource resource = gameService.getImage(id);
        log.info("Finished getting an image.");
        return resource == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
    }
}
