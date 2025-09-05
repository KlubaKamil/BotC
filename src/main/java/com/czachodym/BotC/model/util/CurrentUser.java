package com.czachodym.BotC.model.util;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class CurrentUser {
    private long userId;
    private String username;
    private Set<GroupRole> groupRoles;
}
