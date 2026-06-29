package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.DiscordEntityRepository;
import com.czachodym.BotC.dao.DiscordGuildRepository;
import com.czachodym.BotC.model.DiscordEntity;
import com.czachodym.BotC.model.DiscordGuild;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.KafkaResponseListener;
import com.czachodym.botcshared.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscordService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, DiscordNotification> kafkaDiscordTemplate;
    private final KafkaResponseListener kafkaResponseListener;
    @Value("${kafka.topics.notification}")
    private String notificationTopic;
    @Value("${kafka.topics.channels}")
    private String channelsTopic;
    private final DiscordGuildRepository discordGuildRepository;
    private final DiscordEntityRepository discordEntityRepository;
    private final GameService gameService;
    private final ScriptService scriptService;
    private final CharacterService characterService;
    private final PlayerService playerService;
    private final AchievementService achievementService;
    private final DtoMapper dtoMapper;

    @Transactional
    public void saveDiscordChannels(Group group, DiscordRootDto discordRootDto){
        DiscordRootDto groupDiscordServers = getAllGroupDiscordServers(group);
        List<String> groupDiscordEntitiesAsIdsList = getDiscordRootAsChannelAndThreadIdsList(groupDiscordServers, false);
        log.info(String.valueOf(groupDiscordEntitiesAsIdsList));
        List<DiscordEntity> entitiesToSave = new ArrayList<>();

        discordRootDto.servers()
                .forEach(server -> {
                    server.channels().forEach(channel -> {
                            if(channel.allowed() && groupDiscordEntitiesAsIdsList.contains(channel.discordChannelId())) {
                                entitiesToSave.add(DiscordEntity.builder()
                                        .discordEntityId(channel.discordChannelId())
                                        .guildId(server.discordGuildId())
                                        .groups(Set.of(group))
                                        .build());
                            }
                            channel.threads().forEach(thread -> {
                                if(thread.allowed() && groupDiscordEntitiesAsIdsList.contains(thread.discordThreadId())) {
                                    entitiesToSave.add(DiscordEntity.builder()
                                            .discordEntityId(thread.discordThreadId())
                                            .guildId(server.discordGuildId())
                                            .groups(Set.of(group))
                                            .build());
                                }
                            });
                        });
                    }
                );

        discordEntityRepository.deleteAllByGroups_Id(group.getId());
        discordEntityRepository.saveAll(entitiesToSave);
    }

    public void notifyDiscordService(Group group, DiscordNotification discordNotification){
        List<String> savedDiscordEntityIds = discordEntityRepository.findAllByGroups_Id(group.getId())
                .stream()
                .map(DiscordEntity::getDiscordEntityId)
                .toList();
        List<String> requestDiscordEntitiesAsIdsList = getDiscordRootAsChannelAndThreadIdsList(discordNotification.discordRootDto(), true).stream()
                .filter(savedDiscordEntityIds::contains)
                .toList();

        discordNotification = discordNotification.toBuilder()
                        .channelsToNotify(requestDiscordEntitiesAsIdsList)
                        .message(getMessage(discordNotification, group))
                        .resources(getResources(discordNotification))
                        .build();
        kafkaDiscordTemplate.send(notificationTopic, discordNotification);
    }

    public DiscordRootDto getAllFilteredGroupDiscordServers(Group group){
        return getRebuiltDiscordRoot(group, true);
    }

    public DiscordRootDto getAllGroupDiscordServers(Group group) {
        return getRebuiltDiscordRoot(group, false);
    }

    private DiscordRootDto getRebuiltDiscordRoot(Group group, boolean filterNotAllowed){
        List<String> savedGroupDiscordServerIds = discordGuildRepository.findAllByGroups_Id(group.getId()).stream()
                .map(DiscordGuild::getDiscordGuildId)
                .toList();
        List<DiscordGuildDto> groupDiscordServers = getAllDiscordServers().servers().stream()
                .filter(s -> savedGroupDiscordServerIds.contains(s.discordGuildId()))
                .toList();

        List<String> savedDiscordEntityIds = discordEntityRepository.findAllByGroups_Id(group.getId())
                .stream()
                .map(DiscordEntity::getDiscordEntityId)
                .toList();

        List<DiscordGuildDto> rebuiltServers = rebuildServers(groupDiscordServers, savedDiscordEntityIds, filterNotAllowed);

        return DiscordRootDto.builder()
                .servers(rebuiltServers)
                .build();
    }

    private List<DiscordGuildDto> rebuildServers(List<DiscordGuildDto> groupDiscordServers, List<String> savedDiscordEntities, boolean filterNotAllowed){
        return groupDiscordServers.stream()
                .map(server -> server.toBuilder()
                        .channels(rebuildChannels(server, savedDiscordEntities, filterNotAllowed))
                        .build())
                .filter(server -> !server.channels().isEmpty())
                .toList();
    }

    private List<DiscordChannelDto> rebuildChannels(DiscordGuildDto groupDiscordServer, List<String> savedDiscordEntities, boolean filterNotAllowed){
        return groupDiscordServer.channels().stream()
                .map(channel -> channel.toBuilder()
                        .threads(rebuildThreads(channel, savedDiscordEntities, filterNotAllowed))
                        .allowed(savedDiscordEntities.contains(channel.discordChannelId()) || !channel.threads().isEmpty())
                        .build())
                .filter(channel -> !filterNotAllowed || channel.allowed())
                .filter(channel -> channel.channelType() == ChannelType.TEXT || !channel.threads().isEmpty())
                .map(channel -> channel.toBuilder().allowed(channel.allowed() && !filterNotAllowed).build())
                .toList();
    }

    private List<DiscordThreadDto> rebuildThreads(DiscordChannelDto groupDiscordChannel, List<String> savedDiscordEntities, boolean filterNotAllowed){
        return groupDiscordChannel.threads().stream()
                .map(thread -> thread.toBuilder()
                        .allowed(savedDiscordEntities.contains(thread.discordThreadId()))
                        .build())
                .filter(thread -> !filterNotAllowed || thread.allowed())
                .map(thread -> thread.toBuilder().allowed(thread.allowed() && !filterNotAllowed).build())
                .toList();
    }

    private List<String> getDiscordRootAsChannelAndThreadIdsList(DiscordRootDto discordRootDto, boolean filterNotAllowed){
        List<String> groupDiscordEntitiesAsIdsList = new ArrayList<>();
        discordRootDto.servers()
                .forEach(server -> server.channels()
                        .forEach(channel -> {
                            if((!filterNotAllowed || channel.allowed()) && channel.channelType() == ChannelType.TEXT) {
                                groupDiscordEntitiesAsIdsList.add(channel.discordChannelId());
                            }
                            channel.threads()
                                    .forEach(thread -> {
                                        if(!filterNotAllowed || thread.allowed()) {
                                            groupDiscordEntitiesAsIdsList.add(thread.discordThreadId());
                                        }
                                    });
                        }));
        return groupDiscordEntitiesAsIdsList;
    }

    private DiscordRootDto getAllDiscordServers(){
        String correlationId = UUID.randomUUID().toString();

        JSONObject request = new JSONObject();
        request.put("correlationId", correlationId);
        kafkaTemplate.send(channelsTopic, request.toString());
        DiscordRootDto response = null;

        try {
            response = kafkaResponseListener.waitForResponse(correlationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private String getMessage(DiscordNotification discordNotification, Group group){
        long id = discordNotification.id();
        NotificationType type = discordNotification.notificationType();
        NotificationMode notificationMode = discordNotification.notificationMode();
        long groupId = group.getId();

        return switch(type){
            case GAME -> gameService.getMessage(id, notificationMode, groupId);
            case SCRIPT -> scriptService.getMessage(id, notificationMode, groupId);
            case CHARACTER -> characterService.getMessage(id, notificationMode, groupId);
            case PLAYER -> playerService.getMessage(id, notificationMode, groupId);
            case ACHIEVEMENT -> achievementService.getMessage(id, notificationMode, groupId);
        };
    }

    private List<byte[]> getResources(DiscordNotification discordNotification){
        if(discordNotification.notificationType() != NotificationType.GAME){
            return Collections.emptyList();
        }
        long id = discordNotification.id();

        return gameService.getImages(id);
    }
}
