package com.czachodym.BotC.dto.details.achievement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AchievementPlayerDetails (
    Long id,
    String name,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate date
){}
