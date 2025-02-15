package com.czachodym.BotC.dto;

import com.czachodym.BotC.model.Alignment;
import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public record CharacterDto(
        Long id,
        @Nonnull
        String name,
        @Nonnull
        Alignment alignment,
        boolean good,
        @Nonnull
        String description,
        String linkToWiki
){}
