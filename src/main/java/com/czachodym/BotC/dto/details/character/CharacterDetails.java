package com.czachodym.BotC.dto.details.character;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record CharacterDetails (
        long gamesNumber,
        long wonGamesNumber,
        double winRatio,
        List<CharacterInScriptDetails> characterInScriptsDetails


){
    public CharacterDetails(long gamesNumber, long wonGamesNumber) {
        this(
            gamesNumber,
            wonGamesNumber,
            gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber,
            null
        );
    }
}
