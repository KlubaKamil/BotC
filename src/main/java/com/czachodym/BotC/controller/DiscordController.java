package com.czachodym.BotC.controller;

import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.DiscordService;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.DiscordNotification;
import com.czachodym.botcshared.dto.DiscordRootDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discord")
@Slf4j
@RequiredArgsConstructor
public class DiscordController {
    private final DiscordService discordService;
    private final Validators validators;

    @GetMapping("/{groupId}/allowed")
    public ResponseEntity<DiscordRootDto> getAllowedGroupDiscordChannels(@PathVariable long groupId){
        log.info("Getting allowed discord channels for group: {}.", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        return ResponseEntity.ok(discordService.getAllFilteredGroupDiscordServers(group));
    }

    @GetMapping("/{groupId}/all")
    public ResponseEntity<DiscordRootDto> getGroupDiscordServers(@PathVariable long groupId){
        log.info("Getting all discord channels for group: {}.", groupId);
        Group group = validators.throwIfGroupNotAvailableAdmin(groupId);
        return ResponseEntity.ok(discordService.getAllGroupDiscordServers(group));
    }

    @PostMapping("{groupId}/channels")
    public void saveDiscordChannels(@PathVariable long groupId, @RequestBody DiscordRootDto discordRootDto){
        log.info("Discord channels save request: {}, {}", groupId, discordRootDto);
        Group group = validators.throwIfGroupNotAvailableAdmin(groupId);
        discordService.saveDiscordChannels(group, discordRootDto);
        log.info("Save done.");
    }

    @PostMapping("/notifications/{groupId}")
    public void sendDiscordNotification(@PathVariable long groupId, @RequestBody DiscordNotification discordNotification){
        log.info("Discord notification request: {}", discordNotification);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        discordService.notifyDiscordService(group, discordNotification);
        log.info("Notification sent.");
    }
}
