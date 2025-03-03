package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.service.PlayerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id){
        log.info("Getting a player: {}", id);
        PlayerDto playerDto = playerService.getPlayer(id);
        log.info("Finished getting a player");
        return ResponseEntity.ok(playerDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        log.info("Getting all players.");
        List<PlayerDto> playerDtos = playerService.getAllPlayers();
        log.info("Finished getting all players");
        return ResponseEntity.ok(playerDtos);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createPlayer(@Valid @RequestBody PlayerDto playerDto){
        log.info("Creating new player: {}", playerDto);
        long id = playerService.createPlayer(playerDto);
        log.info("Finished creating new player.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editPlayer(@Valid @RequestBody PlayerDto playerRenameDto){
        log.info("Renaming a player: {}", playerRenameDto);
        long id = playerService.editPlayer(playerRenameDto);
        log.info("Finished renaming a player.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePlayer(@PathVariable("id") long id){
        log.info("Deleting a player: {}", id);
        playerService.deletePlayer(id);
        log.info("Finished deleting a player.");
    }
}
