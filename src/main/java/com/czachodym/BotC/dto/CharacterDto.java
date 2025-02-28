package com.czachodym.BotC.dto;

import com.czachodym.BotC.model.Alignment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CharacterDto(
        Long id,
        @NotBlank
        String name,
        @NotNull
        Alignment alignment,
        @NotBlank
        String description,
        String linkToWiki
){}
