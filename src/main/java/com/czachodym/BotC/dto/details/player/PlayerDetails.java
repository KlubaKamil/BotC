package com.czachodym.BotC.dto.details.player;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayerDetails (
    List<PlayerCharacterDetails> playerCharactersDetails,
    List<PlayerScriptDetails> playerScriptsDetails
){}
