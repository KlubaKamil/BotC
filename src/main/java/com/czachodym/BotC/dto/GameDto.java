package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.util.PlayerCharacterPairDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GameDto (
        long id,
        ScriptDto script,
        PlayerDto storyteller,
        List<PlayerCharacterPairDto> assignments,
        boolean goodWon,
        LocalDateTime date
){}
