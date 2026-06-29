package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.AchievementRepository;
import com.czachodym.BotC.dto.AchievementDto;
import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.dto.details.achievement.AchievementPlayerDetails;
import com.czachodym.BotC.dto.headers.AchievementHeader;
import com.czachodym.BotC.model.Achievement;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final AchievementRepository achievementRepository;
    private final DtoMapper dtoMapper;
    private final Validators validators;

    public AchievementDto getAchievement(long id, long groupId){
        log.info("Checking if achievement exists.");
        Achievement achievement = validators.throwIfNotFoundByIdAndGroupId(id, groupId, achievementRepository);
        log.info("Achievement found, getting details.");
        AchievementDetails achievementDetails = achievementRepository.findAchievementDetails(id);
        List<AchievementPlayerDetails> achievementPlayerDetails = achievementRepository.findAchievementPlayerDetails(id);
        achievementDetails = achievementDetails.toBuilder()
                .achievementPlayerDetails(achievementPlayerDetails)
                .build();
        return dtoMapper.mapAchievement(achievement, achievementDetails);
    }

    public List<AchievementDto> getAllGroupAchievements(long groupId){
        log.info("Getting all achievements");
        List<Achievement> achievements = achievementRepository.findByGroups_Id(groupId);
        log.info("Achievements found.");
        return dtoMapper.mapAchievementList(achievements);
    }

    public List<AchievementHeader> getAllAchievementHeaders(long groupId){
        log.info("Getting all achievement headers");
        List<AchievementHeader> achievementHeaders = achievementRepository.findAllAchievementHeaders(groupId);
        log.info("Headers found.");
        return achievementHeaders;
    }

    public long createAchievement(long groupId, AchievementDto achievementDto){
        String name = achievementDto.name();
        log.info("Checking if achievement exists.");
        validators.throwIfExistsByNameAndGroupId(groupId, name, achievementRepository);
        Achievement achievement = buildAchievement(groupId, achievementDto);
        log.info("Saving new achievement.");
        Achievement savedAchievement = achievementRepository.save(achievement);
        long id = savedAchievement.getId();
        log.info("Achievement saved: {}.", id);

        return id;
    }

    public long editAchievement(long groupId, AchievementDto achievementDto){
        long id = achievementDto.id();
        String name = achievementDto.name();
        log.info("Checking if achievement exists.");
        Achievement achievement = validators.throwIfNotFoundByIdAndGroupId(id, groupId, achievementRepository);
        if(!achievement.getName().equals(achievementDto.name())) {
            validators.throwIfExistsByNameAndGroupId(groupId, name, achievementRepository);
        }
        log.info("Achievement found, updating.");
        Achievement savedAchievement = buildAchievement(groupId, achievementDto, achievement);
        achievementRepository.save(savedAchievement);
        log.info("Achievement updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deleteAchievement(long id, long groupId){
        log.info("Deleting a achievement.");

        log.info("Checking if group available: {}.", groupId);
        validators.throwIfGroupNotAvailableMod(groupId);
        log.info("Checking if achievement exists: {}.", id);
        validators.throwIfNotFoundByIdAndGroupId(id, groupId, achievementRepository);
        achievementRepository.deleteById(id);
        boolean deleted = achievementRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    public String getMessage(long id, NotificationMode notificationMode, long groupId){
        Achievement achievement = validators.throwIfNotFoundByIdAndGroupId(id, groupId, achievementRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nowe osiągnięcie!" : "Edytowano osiągnięcie!";
        String name = achievement.getName();
        String description = achievement.getDescription();
        return """
                %s
                Id: %d
                Name: %s
                Opis: %s
                Kliknij i zobacz: %s/achievements/%d
                """.formatted(modeMessage, id, name, description, FRONTEND_URL, id);
    }

    private Achievement buildAchievement(long groupId, AchievementDto achievementDto){
        return buildAchievement(groupId, achievementDto, new Achievement());
    }

    private Achievement buildAchievement(long groupId, AchievementDto achievementDto, Achievement achievement){
        log.info("Validating a characterDto.");

        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<Group> groups = achievement.getGroups();
        groups.add(group);
        log.info("Group available.");

        log.info("Validation successful, building achievement.");
        return achievement.toBuilder()
                .groups(groups)
                .name(achievementDto.name())
                .description(achievementDto.description())
                .build();
    }
}
