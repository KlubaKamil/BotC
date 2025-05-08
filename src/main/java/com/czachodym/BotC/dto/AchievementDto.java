package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import lombok.Builder;

@Builder
public record AchievementDto(
        long id,
        String name,
        String description,
        AchievementDetails achievementDetails
){}
