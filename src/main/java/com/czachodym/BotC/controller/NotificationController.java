package com.czachodym.BotC.controller;

import com.czachodym.BotC.model.util.NotificationType;
import com.czachodym.BotC.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("{type}/{id}")
    public void sendDiscordNotification(@PathVariable NotificationType type, @PathVariable Long id){
        log.info("Notification request: {}, {}", type, id);
        notificationService.notifyDiscordService(type, id);
        log.info("Notification sent.");
    }
}
