package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.player.PlayerDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record PlayerDto(
        long id,
        @NotBlank
        String name,
        PlayerDetails playerDetails
){}
