package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.util.PlayerAchievementDto;
import com.czachodym.BotC.model.Group;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;
import java.util.Set;


@Builder
public record PlayerDto(
        long id,
        Set<Group> groups,
        @NotBlank
        String name,
        String discordName,
        List<PlayerAchievementDto> playerAchievements,
        PlayerDetails playerDetails
){}
