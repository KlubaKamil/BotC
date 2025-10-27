package com.czachodym.BotC.dto.util;

import com.czachodym.BotC.dto.CharacterDto;
import lombok.Builder;

@Builder
public record ScriptCharacterDto (
        CharacterDto character,
        long characterOrder
){}
