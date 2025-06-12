package com.czachodym.BotC.dto.details.achievement;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record AchievementDetails (
        Long accomplishmentNumber,
        List<AchievementPlayerDetails> achievementPlayerDetails
){
    public AchievementDetails(long accomplishmentNumber) {
        this(accomplishmentNumber, null);
    }
}
