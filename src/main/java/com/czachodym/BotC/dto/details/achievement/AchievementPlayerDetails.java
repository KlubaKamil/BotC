package com.czachodym.BotC.dto.details.achievement;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AchievementPlayerDetails (
    long id,
    String name,
    LocalDate date
){}
