package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.AchievementRepository;
import com.czachodym.BotC.dao.PlayerRepository;
import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.dto.details.player.PlayerCharacterDetails;
import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.details.player.PlayerScriptDetails;
import com.czachodym.BotC.dto.headers.PlayerHeader;
import com.czachodym.BotC.dto.util.PlayerAchievementDto;
import com.czachodym.BotC.model.Achievement;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.model.Player;
import com.czachodym.BotC.model.util.PlayerAchievement;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final PlayerRepository playerRepository;
    private final AchievementRepository achievementRepository;
    private final DtoMapper dtoMapper;
    private final Validators validators;

    public PlayerDto getPlayer(long id, long groupId){
        log.info("Checking if player exists.");
        Player player = validators.throwIfNotFoundByIdAndGroupId(id, groupId, playerRepository);
        log.info("Player found, getting details.");
        PlayerDetails playerDetails = playerRepository.findPlayerDetails(id);
        List<PlayerScriptDetails> playerScriptsDetails = playerRepository.findPlayerScriptDetailsById(id);
        List<PlayerCharacterDetails> playerCharactersDetails = playerRepository.findPlayerCharacterDetailsById(id);
        playerDetails = playerDetails.toBuilder()
                .playerScriptsDetails(playerScriptsDetails)
                .playerCharactersDetails(playerCharactersDetails)
                .build();
        return dtoMapper.mapPlayer(player, playerDetails);
    }

    public List<PlayerDto> getAllPlayers(long groupId){
        log.info("Getting all players");
        List<Player> players = playerRepository.findByGroups_Id(groupId);
        log.info("Players found.");
        return dtoMapper.mapPlayerList(players);
    }

    public List<PlayerHeader> getAllPlayerHeaders(long groupId){
        log.info("Getting all player headers");
        List<PlayerHeader> playerHeaders = playerRepository.findAllPlayerHeaders(groupId);
        log.info("Headers found.");
        return playerHeaders;
    }

    public long createPlayer(long groupId, PlayerDto playerDto){
        String name = playerDto.name();
        log.info("Checking if player exists.");
        validators.throwIfExistsByNameAndGroupId(groupId, name, playerRepository);
        Player player = buildPlayer(groupId, playerDto);
        log.info("Saving new player.");
        Player savedPlayer = playerRepository.save(player);
        long id = savedPlayer.getId();
        log.info("Player saved: {}.", id);

        return id;
    }

    public long editPlayer(long groupId, PlayerDto playerDto){
        long id = playerDto.id();
        String name = playerDto.name();
        log.info("Checking if player exists.");
        Player player = validators.throwIfNotFoundByIdAndGroupId(id, groupId, playerRepository);
        if(!player.getName().equals(playerDto.name())) {
            validators.throwIfExistsByNameAndGroupId(groupId, name, playerRepository);
        }
        log.info("Player found, updating.");
        Player savedPlayer = buildPlayer(groupId, playerDto, player);
        playerRepository.save(savedPlayer);
        log.info("Player updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deletePlayer(long id, long groupId){
        log.info("Deleting a player.");

        log.info("Checking if group available: {}.", groupId);
        validators.throwIfGroupNotAvailableMod(groupId);
        log.info("Checking if player exists: {}.", id);
        validators.throwIfNotFoundByIdAndGroupId(id, groupId, playerRepository);
        playerRepository.deleteById(id);
        boolean exists = playerRepository.existsById(id);

        log.info("Deleted: {}", !exists);
    }

    public String getMessage(long id, NotificationMode notificationMode){
        Player player = validators.throwIfNotFoundByIdAndGroupId(id, 0, playerRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nowego gracza!" : "Edytowano gracza!";
        String name = player.getName();
        String discordName = player.getDiscordName() == null ? "-" : player.getDiscordName();
        return """
                %s
                Id: %d
                Imię: %s
                Nick na Discordzie: %s
                Kliknij i zobacz: %s/players/%d
                """.formatted(modeMessage, id, name, discordName, FRONTEND_URL, id);
    }

    private Player buildPlayer(long groupId, PlayerDto playerDto){
        return buildPlayer(groupId, playerDto, new Player());
    }

    private Player buildPlayer(long groupId, PlayerDto playerDto, Player player){
        log.info("Validating a playerDto. Looking for playerAchievements");

        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<Group> groups = player.getGroups();
        groups.add(group);

        log.info("Group available, looking for achievements.");
        List<PlayerAchievementDto> playerAchievementDtos = playerDto.playerAchievements();
        List<Long> achievementIds = playerAchievementDtos.stream()
                .map(a -> a.achievement().id())
                .toList();
        List<Achievement> achievements = validators.throwIfEntitiesNotExistByGroupId(achievementIds, groupId, achievementRepository);
        List<PlayerAchievement> playerAchievements = playerAchievementDtos.stream()
                .map(pad -> {
                    Long padId = pad.id();
                    Achievement achievement = achievements.stream()
                            .filter(a -> pad.achievement().id() == a.getId()).findFirst().orElseThrow();
                    return PlayerAchievement.builder()
                            .id(padId)
                            .achievement(achievement)
                            .date(pad.date() == null ? LocalDate.now() : pad.date())
                            .build();
                })
                .toList();
        log.info("Achievements found.");

        log.info("Validation successful, building player.");
        return player.toBuilder()
                .groups(groups)
                .name(playerDto.name())
                .discordName(playerDto.discordName())
                .playerAchievements(playerAchievements)
                .build();
    }
}
