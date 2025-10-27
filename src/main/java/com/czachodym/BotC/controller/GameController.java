package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.dto.util.BalanceMarkDto;
import com.czachodym.BotC.service.GameService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/game/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a game, id: {}, groupId: {}", id, groupId);
        GameDto gameDto = gameService.getGame(id, groupId);
        log.info("Finished getting a game");
        return ResponseEntity.ok(gameDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GameDto>> getAllGames(@PathVariable long groupId){
        log.info("Getting all games for groupId: {}.", groupId);
        List<GameDto> gameDtos = gameService.getAllGames(groupId);
        log.info("Finished getting all games");
        return ResponseEntity.ok(gameDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<GameHeader>> getAllGameHeaders(@PathVariable long groupId){
        log.info("Getting all game headers for groupId: {}.", groupId);
        List<GameHeader> gameDtos = gameService.getAllGameHeaders(groupId);
        log.info("Finished getting all game headers");
        return ResponseEntity.ok(gameDtos);
    }
    
    @PutMapping
    public ResponseEntity<Map<String, Long>> addGame(@PathVariable long groupId, @RequestBody GameDto gameDto){
        log.info("Creating new game, groupId: {}, dto: {}", groupId, gameDto);
        long id = gameService.createGame(groupId, gameDto);
        log.info("Finished creating new game.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editGame(@PathVariable long groupId, @RequestBody GameDto gameDto){
        log.info("Editing an existing game, groupId: {}, dto: {}", groupId, gameDto);
        long id = gameService.editGame(groupId, gameDto);
        log.info("Finished editing an existing game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteGame(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a game, id: {}, groupId: {}", id, groupId);
        gameService.deleteGame(id, groupId);
        log.info("Finished deleting a game");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable long id, @PathVariable long groupId) {
        log.info("Getting an image for game, id: {}, groupId: {}", id, groupId);
        Resource resource = gameService.getImage(id, groupId);
        log.info("Finished getting an image.");
        return resource == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
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
        log.info("Uploading an image for game, id: {}, groupId: {}", id, groupId);
        boolean success = gameService.uploadImage(id, groupId, image);
        log.info("Finished uploading an image.");
        return success ? ResponseEntity.status(returnStatus).body(Map.of("id", id)) :
                ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{gameId}/balance")
    public ResponseEntity<Map<String, String>> addBalanceMarkPut(@PathVariable long groupId, @PathVariable long gameId,
                                                              @RequestBody BalanceMarkDto balanceMarkDto) {
        return addBalanceMark(groupId, gameId, balanceMarkDto, CREATED);
    }
    @PostMapping("/{gameId}/balance")
    public ResponseEntity<Map<String, String>> addBalanceMarkPost(@PathVariable long groupId, @PathVariable long gameId,
                                                              @RequestBody BalanceMarkDto balanceMarkDto) {
        return addBalanceMark(groupId, gameId, balanceMarkDto, OK);
    }
    public ResponseEntity<Map<String, String>> addBalanceMark(long groupId, long gameId, BalanceMarkDto balanceMarkDto,
                                                              HttpStatus returnStatus){
        log.info("Adding a balance mark to game, groupId: {}, gameId: {}, balance mark: {}", groupId, gameId, balanceMarkDto);
        String username = gameService.addBalanceMark(groupId, gameId, balanceMarkDto);
        return ResponseEntity.status(returnStatus).body(Map.of("username", username));
    }

    @DeleteMapping("/{gameId}/balance/{username}")
    @ResponseStatus(NO_CONTENT)
    public void deleteBalnceMark(@PathVariable long groupId, @PathVariable long gameId, @PathVariable String username){
        log.info("Deleting a balance mark, groupId: {}, gameId: {}, username: {}", groupId, gameId, username);
        gameService.deleteBalanceMark(groupId, gameId, username);
        log.info("Finished deleting a balance mark.");
    }
}
