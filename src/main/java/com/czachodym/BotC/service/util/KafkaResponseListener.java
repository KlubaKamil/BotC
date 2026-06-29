package com.czachodym.BotC.service.util;

import com.czachodym.botcshared.dto.DiscordRootDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaResponseListener {
    private final Map<String, CompletableFuture<DiscordRootDto>> futureMap = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public DiscordRootDto waitForResponse(String correlationId) throws Exception {
        CompletableFuture<DiscordRootDto> future = new CompletableFuture<>();
        futureMap.put(correlationId, future);
        return future.get(5, TimeUnit.SECONDS);
    }

    @KafkaListener(
            topics = "${kafka.topics.channels-response}",
            groupId = "${spring.kafka.consumer.group-id}",
            autoStartup = "${listen.auto.start:false}")
    public void listenForDiscordChannelsMessage(String message) {
        JSONObject json = new JSONObject(message);
        String correlationId = json.getString("correlationId");
        String payload = json.getString("payload");
        DiscordRootDto discordRootDto;
        try {
            discordRootDto = objectMapper.readValue(payload, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        CompletableFuture<DiscordRootDto> future = futureMap.remove(correlationId);
        if (future != null) {
            future.complete(discordRootDto);
        }
    }
}