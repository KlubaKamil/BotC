package com.czachodym.BotC.service;

import com.czachodym.BotC.service.util.KafkaResponseListener;
import com.czachodym.botcshared.dto.DiscordGuild;
import com.czachodym.botcshared.dto.DiscordNotification;
import com.czachodym.botcshared.dto.NotificationMode;
import com.czachodym.botcshared.dto.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, DiscordNotification> kafkaDiscordTemplate;
    private final KafkaResponseListener kafkaResponseListener;
    @Value("${kafka.topics.notification}")
    private String notificationTopic;
    @Value("${kafka.topics.channels}")
    private String channelsTopic;
    private final GameService gameService;
    private final ScriptService scriptService;
    private final CharacterService characterService;
    private final PlayerService playerService;
    private final AchievementService achievementService;

    public void notifyDiscordService(DiscordNotification discordNotification){
        String message = getMessage(discordNotification);
        discordNotification = discordNotification.toBuilder()
                        .message(message)
                        .build();
        kafkaDiscordTemplate.send(notificationTopic, discordNotification);
    }

    public List<DiscordGuild> getDiscordChannels(){
        String correlationId = UUID.randomUUID().toString();

        JSONObject request = new JSONObject();
        request.put("correlationId", correlationId);
        kafkaTemplate.send(channelsTopic, request.toString());
        List<DiscordGuild> response = null;

        try {
            response = kafkaResponseListener.waitForResponse(correlationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private String getMessage(DiscordNotification discordNotification){
        long id = discordNotification.id();
        NotificationType type = discordNotification.notificationType();
        NotificationMode notificationMode = discordNotification.notificationMode();

        return switch(type){
            case GAME -> gameService.getMessage(id, notificationMode);
            case SCRIPT -> scriptService.getMessage(id, notificationMode);
            case CHARACTER -> characterService.getMessage(id, notificationMode);
            case PLAYER -> playerService.getMessage(id, notificationMode);
            case ACHIEVEMENT -> achievementService.getMessage(id, notificationMode);
        };
    }
}
