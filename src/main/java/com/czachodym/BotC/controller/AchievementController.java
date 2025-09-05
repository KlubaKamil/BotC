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
@RequestMapping("/achievement/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievement(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a achievement, id: {}, groupId: {}", id, groupId);
        AchievementDto achievementDto = achievementService.getAchievement(id, groupId);
        log.info("Finished getting a achievement");
        return ResponseEntity.ok(achievementDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AchievementDto>> getAllGroupAchievements(@PathVariable long groupId){
        log.info("Getting all achievements for group: {}.", groupId);
        List<AchievementDto> achievementDtos = achievementService.getAllGroupAchievements(groupId);
        log.info("Finished getting all achievements");
        return ResponseEntity.ok(achievementDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<AchievementHeader>> getAllGroupAchievementHeaders(@PathVariable long groupId){
        log.info("Getting all achievement headers for group: {}.", groupId);
        List<AchievementHeader> achievementHeaders = achievementService.getAllAchievementHeaders(groupId);
        log.info("Finished getting all achievement headers");
        return ResponseEntity.ok(achievementHeaders);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createAchievement(@PathVariable long groupId, @Valid @RequestBody AchievementDto achievementDto){
        log.info("Creating new achievement, groupId: {}, dto: {}", groupId, achievementDto);
        long id = achievementService.createAchievement(groupId, achievementDto);
        log.info("Finished creating new achievement.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editAchievement(@PathVariable long groupId, @Valid @RequestBody AchievementDto achievementRenameDto){
        log.info("Editing a achievement, groupId: {}, dto: {}", groupId, achievementRenameDto);
        long id = achievementService.editAchievement(groupId, achievementRenameDto);
        log.info("Finished editing a achievement.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAchievement(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a achievement, id: {}, groupId: {}", id, groupId);
        achievementService.deleteAchievement(id, groupId);
        log.info("Finished deleting a achievement.");
    }
}
