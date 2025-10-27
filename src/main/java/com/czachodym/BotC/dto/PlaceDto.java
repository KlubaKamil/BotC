package com.czachodym.BotC.dto;

import com.czachodym.BotC.model.Group;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

@Builder
public record PlaceDto(
        long id,
        Set<Group> groups,
        @NotBlank
        String name
){}
