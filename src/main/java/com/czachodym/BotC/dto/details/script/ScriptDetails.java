package com.czachodym.BotC.dto.details.script;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ScriptDetails (
    long gamesNumber,
    double choicePercentage,
    List<ScriptCharacterDetails> scriptCharactersDetails
){
    public ScriptDetails(long scriptGamesNumber, long totalGamesNumber) {
        this(
            scriptGamesNumber,
            totalGamesNumber == 0 ? 0 : 100.0 * scriptGamesNumber / totalGamesNumber,
            null
        );
    }
}
