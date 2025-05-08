package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.util.PlayerAchievementDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;


@Builder
public record PlayerDto(
        long id,
        @NotBlank
        String name,
        String discordName,
        List<PlayerAchievementDto> playerAchievements,
        PlayerDetails playerDetails
){}
