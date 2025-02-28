package com.czachodym.BotC.dto.util;


import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.PlayerDto;
import lombok.Builder;

@Builder
public record PlayerCharacterPairDto(
        PlayerDto player,
        CharacterDto character,
        boolean good
){}
