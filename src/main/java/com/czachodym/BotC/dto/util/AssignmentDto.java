package com.czachodym.BotC.dto.util;


import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.PlayerDto;
import lombok.Builder;

import java.util.List;

@Builder
public record AssignmentDto(
        PlayerDto player,
        CharacterDto character,
        int index,
        boolean good,
        List<TransformationDto> transformations
){}
