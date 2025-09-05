package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.dto.headers.PlayerHeader;
import com.czachodym.BotC.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/player/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a player, id: {}, groupId: {}", id, groupId);
        PlayerDto playerDto = playerService.getPlayer(id, groupId);
        log.info("Finished getting a player");
        return ResponseEntity.ok(playerDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(@PathVariable long groupId){
        log.info("Getting all players for groupId: {}.", groupId);
        List<PlayerDto> playerDtos = playerService.getAllPlayers(groupId);
        log.info("Finished getting all players");
        return ResponseEntity.ok(playerDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<PlayerHeader>> getAllPlayerHeaders(@PathVariable long groupId){
        log.info("Getting all player headers for groupId: {}.", groupId);
        List<PlayerHeader> playerHeaders = playerService.getAllPlayerHeaders(groupId);
        log.info("Finished getting all player headers");
        return ResponseEntity.ok(playerHeaders);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createPlayer(@PathVariable long groupId, @Valid @RequestBody PlayerDto playerDto){
        log.info("Creating new player, groupId: {}, dto: {}", groupId, playerDto);
        long id = playerService.createPlayer(groupId, playerDto);
        log.info("Finished creating new player.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editPlayer(@PathVariable long groupId, @Valid @RequestBody PlayerDto playerDto){
        log.info("Editing a player, groupId: {}, dto: {}", groupId, playerDto);
        long id = playerService.editPlayer(groupId, playerDto);
        log.info("Finished editing a player.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePlayer(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a player, id: {}, groupId: {}", id, groupId);
        playerService.deletePlayer(id, groupId);
        log.info("Finished deleting a player.");
    }
}
