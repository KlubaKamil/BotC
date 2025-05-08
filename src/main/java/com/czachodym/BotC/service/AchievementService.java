package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.AchievementRepository;
import com.czachodym.BotC.dto.AchievementDto;
import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.dto.details.achievement.AchievementPlayerDetails;
import com.czachodym.BotC.dto.headers.AchievementHeader;
import com.czachodym.BotC.model.Achievement;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.throwIfExistsByName;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final DtoMapper dtoMapper;

    public AchievementDto getAchievement(long id){
        log.info("Checking if achievement exists.");
        Achievement achievement = throwIfNotFoundById(id, achievementRepository);
        log.info("Achievement found, getting details.");
        AchievementDetails achievementDetails = achievementRepository.findAchievementDetails(id);
        List<AchievementPlayerDetails> achievementPlayerDetails = achievementRepository.findAchievementPlayerDetails(id);
        achievementDetails = achievementDetails.toBuilder()
                .achievementPlayerDetails(achievementPlayerDetails)
                .build();
        return dtoMapper.mapAchievement(achievement, achievementDetails);
    }

    public List<AchievementDto> getAllAchievements(){
        log.info("Getting all playerAchievements");
        List<Achievement> achievements = achievementRepository.findAll();
        log.info("Achievements found.");
        return dtoMapper.mapAchievementList(achievements);
    }

    public List<AchievementHeader> getAllAchievementHeaders(){
        log.info("Getting all achievement headers");
        List<AchievementHeader> achievementHeaders = achievementRepository.findAllAchievementHeaders();
        log.info("Headers found.");
        return achievementHeaders;
    }

    public long createAchievement(AchievementDto achievementDto){
        String name = achievementDto.name();
        log.info("Checking if achievement exists.");
        throwIfExistsByName(name, achievementRepository);
        Achievement achievement = buildAchievement(achievementDto);
        log.info("Saving new achievement.");
        Achievement savedAchievement = achievementRepository.save(achievement);
        long id = savedAchievement.getId();
        log.info("Achievement saved: {}.", id);

        return id;
    }

    public long editAchievement(AchievementDto achievementDto){
        long id = achievementDto.id();
        String name = achievementDto.name();
        log.info("Checking if achievement exists.");
        Achievement achievement = throwIfNotFoundById(id, achievementRepository);
        if(!achievement.getName().equals(achievementDto.name())) {
            throwIfExistsByName(name, achievementRepository);
        }
        log.info("Achievement found, updating.");
        Achievement savedAchievement = buildAchievement(achievementDto, achievement);
        achievementRepository.save(savedAchievement);
        log.info("Achievement updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deleteAchievement(long id){
        log.info("Deleting a achievement.");
        boolean exists = achievementRepository.existsById(id);
        achievementRepository.deleteById(id);
        boolean deleted = exists & !achievementRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    private Achievement buildAchievement(AchievementDto achievementDto){
        return buildAchievement(achievementDto, Achievement.builder());
    }

    private Achievement buildAchievement(AchievementDto achievementDto, Achievement achievement){
        Achievement.AchievementBuilder<?,?> achievementBuilder = achievement.toBuilder()
                .id(achievement.getId());
        return buildAchievement(achievementDto, achievementBuilder);
    }

    private Achievement buildAchievement(AchievementDto achievementDto, Achievement.AchievementBuilder<?,?> builder){
        return builder
                .name(achievementDto.name())
                .description(achievementDto.description())
                .build();
    }
}
