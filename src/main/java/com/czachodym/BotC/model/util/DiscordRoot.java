package com.czachodym.BotC.model.util;

import com.czachodym.BotC.model.DiscordGuild;
import lombok.Builder;

import java.util.List;

@Builder
public record DiscordRoot (
    List<DiscordGuild> servers
){}
