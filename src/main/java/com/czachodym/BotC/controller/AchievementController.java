package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.AchievementDto;
import com.czachodym.BotC.dto.headers.AchievementHeader;
import com.czachodym.BotC.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
@Slf4j
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievement(@PathVariable long id){
        log.info("Getting a achievement: {}", id);
        AchievementDto achievementDto = achievementService.getAchievement(id);
        log.info("Finished getting a achievement");
        return ResponseEntity.ok(achievementDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AchievementDto>> getAllAchievements(){
        log.info("Getting all playerAchievements.");
        List<AchievementDto> achievementDtos = achievementService.getAllAchievements();
        log.info("Finished getting all playerAchievements");
        return ResponseEntity.ok(achievementDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<AchievementHeader>> getAllAchievementHeaders(){
        log.info("Getting all achievement headers.");
        List<AchievementHeader> achievementHeaders = achievementService.getAllAchievementHeaders();
        log.info("Finished getting all achievement headers");
        return ResponseEntity.ok(achievementHeaders);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createAchievement(@Valid @RequestBody AchievementDto achievementDto){
        log.info("Creating new achievement: {}", achievementDto);
        long id = achievementService.createAchievement(achievementDto);
        log.info("Finished creating new achievement.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editAchievement(@Valid @RequestBody AchievementDto achievementRenameDto){
        log.info("Renaming a achievement: {}", achievementRenameDto);
        long id = achievementService.editAchievement(achievementRenameDto);
        log.info("Finished renaming a achievement.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAchievement(@PathVariable("id") long id){
        log.info("Deleting a achievement: {}", id);
        achievementService.deleteAchievement(id);
        log.info("Finished deleting a achievement.");
    }
}
