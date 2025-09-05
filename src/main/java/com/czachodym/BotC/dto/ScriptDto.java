package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.util.ScriptCharacterDto;
import com.czachodym.BotC.model.Group;
import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record ScriptDto(
    long id,
    Set<Group> groups,
    @Nonnull
    String name,
    String author,
    String notes,
    @Nonnull
    List<ScriptCharacterDto> scriptCharacters,
    ScriptDetails scriptDetails
){}
