package com.czachodym.BotC.dto.util;

import com.czachodym.BotC.dto.AchievementDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder()
public record PlayerAchievementDto(
        Long id,
        AchievementDto achievement,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date
){}
