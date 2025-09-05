package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.model.Group;
import lombok.Builder;

import java.util.Set;

@Builder
public record AchievementDto(
        long id,
        Set<Group> groups,
        String name,
        String description,
        AchievementDetails achievementDetails
){}
