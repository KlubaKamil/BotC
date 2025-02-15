package com.czachodym.BotC.dto;

import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.List;

@Builder
public record ScriptDto(
    long id,
    @Nonnull
    String name,
    @Nonnull
    List<CharacterDto> characters,
    int timesPlayed
){}
