package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {
    @Autowired
    private GameService gameService;

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
    
    @PutMapping
    public ResponseEntity<Map<String, Long>> addGame(@RequestBody GameDto gameDto){
        log.info("Creating new game: {}", gameDto);
        long id = gameService.createGame(gameDto);
        log.info("Finished creating new game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editGame(@RequestBody GameDto gameDto){
        log.info("Editing an existing game: {}", gameDto);
        long id = gameService.editGame(gameDto);
        log.info("Finished editing an existing game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteGame(@PathVariable("id") long id){
        log.info("Deleting a game: {}", id);
        boolean deleted = gameService.deleteGame(id);
        log.info("Finished deleting a game");
        return ResponseEntity.ok(Map.of("deleted", deleted));
    }
}
