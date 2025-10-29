package com.czachodym.BotC.dto.util;

import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.model.util.TransformationType;
import lombok.Builder;

@Builder
public record TransformationDto(
        CharacterDto character,
        boolean good,
        TransformationType type
){}
