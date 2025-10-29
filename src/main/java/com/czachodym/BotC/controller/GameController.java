package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.dto.util.BalanceMarkDto;
import com.czachodym.BotC.service.GameService;
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

    @GetMapping("/{id}/images")
    public ResponseEntity<List<String>> getImageNames(@PathVariable long id, @PathVariable long groupId) {
        log.info("Getting an image for game, id: {}, groupId: {}", id, groupId);
        List<String> names = this.gameService.getImageNames(id, groupId);
        log.info("Finished getting an image.");
        return ResponseEntity.ok(names);
    }

    @GetMapping({"/{id}/image/{filename}"})
    public ResponseEntity<Resource> getImage(@PathVariable long id, @PathVariable long groupId, @PathVariable String filename) {
        log.info("Getting an image for game, id: {}, groupId: {}", id, groupId);
        Resource resource = this.gameService.getImage(id, groupId, filename);
        log.info("Finished getting an image.");
        return resource == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
    }

    @PostMapping(
            path = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadImage(
            @PathVariable long groupId,
            @PathVariable long id,
            @RequestPart(value = "imagesToUpload", required = false) List<MultipartFile> imagesToUpload,
            @RequestPart(value = "imagesToDelete", required = false) List<MultipartFile> imagesToDelete) {
        log.info("Uploading images for game, id: {}, groupId: {}", id, groupId);
        boolean success = true;
        if(imagesToDelete != null && !imagesToDelete.isEmpty()) {
            List<String> names = imagesToDelete.stream().map(MultipartFile::getOriginalFilename).toList();
            success = this.gameService.deleteImages(id, groupId, names);
        }
        if(imagesToUpload != null && !imagesToUpload.isEmpty()) {
            success = success && this.gameService.uploadImage(id, groupId, imagesToUpload);
        }
        log.info("Finished uploading images.");
        return success ? ResponseEntity.ok(Map.of("id", id)) :
                ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Map<String, Long>> deleteImages(@PathVariable long id, @PathVariable long groupId, @RequestBody List<String> names) {
        log.info("Deleting images from game, id: {}, groupId: {}, names: {}", id, groupId, names);
        this.gameService.deleteImages(id, groupId, names);
        log.info("Images deleted.");
        return ResponseEntity.ok(Map.of("id", id));
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
    public void deleteBalanceMark(@PathVariable long groupId, @PathVariable long gameId, @PathVariable String username){
        log.info("Deleting a balance mark, groupId: {}, gameId: {}, username: {}", groupId, gameId, username);
        gameService.deleteBalanceMark(groupId, gameId, username);
        log.info("Finished deleting a balance mark.");
    }
}
