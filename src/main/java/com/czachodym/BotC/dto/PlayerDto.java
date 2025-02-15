package com.czachodym.BotC.dto;

import jakarta.annotation.Nonnull;
import lombok.Builder;


@Builder
public record PlayerDto(
        long id,
        @Nonnull
        String name,
        int gamesNumber,
        float winRatio,
        float goodPercentage
){}
