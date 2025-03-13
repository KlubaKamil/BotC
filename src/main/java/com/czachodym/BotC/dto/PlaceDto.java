package com.czachodym.BotC.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlaceDto(
        long id,
        @NotBlank
        String name
){}
