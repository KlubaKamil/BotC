package com.czachodym.BotC.controller;

import com.czachodym.BotC.service.NotificationService;
import com.czachodym.botcshared.dto.DiscordGuild;
import com.czachodym.botcshared.dto.DiscordNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Map<String, List<DiscordGuild>>> getDiscordChannels(){
        log.info("Getting discord channels.");
        return ResponseEntity.ok(Map.of("servers", notificationService.getDiscordChannels()));
    }

    @PostMapping
    public void sendDiscordNotification(@RequestBody DiscordNotification discordNotification){
        log.info("Notification request: {}", discordNotification);
        notificationService.notifyDiscordService(discordNotification);
        log.info("Notification sent.");
    }
}
