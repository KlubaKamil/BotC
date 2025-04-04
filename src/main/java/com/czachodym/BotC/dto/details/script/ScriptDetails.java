package com.czachodym.BotC.dto.details.script;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ScriptDetails (
    double choicePercentage,
    List<ScriptCharacterDetails> scriptCharactersDetails
){
    public ScriptDetails(long scriptGamesNumber, long totalGamesNumber) {
        this(
            totalGamesNumber == 0 ? 0 : 100.0 * scriptGamesNumber / totalGamesNumber,
            null
        );
    }
}
