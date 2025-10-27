package com.czachodym.BotC.dto;

import com.czachodym.BotC.model.util.GroupRole;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto (
    long id,
    String name,
    Set<GroupRole> groupRoles
){}
