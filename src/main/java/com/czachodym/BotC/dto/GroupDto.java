package com.czachodym.BotC.dto;

import lombok.Builder;

@Builder
public record GroupDto (
        long id,
        String name
){}
