package com.czachodym.BotC.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record PlayerDto(
        long id,
        @NotBlank
        String name
){}
