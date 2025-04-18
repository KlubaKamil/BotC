package com.czachodym.BotC.dto.details.character;

public record CharacterInScriptDetails (
        long id,
        String scriptName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public CharacterInScriptDetails(long id, String scriptName, long gamesNumber, long wonGamesNumber) {
        this(
                id,
                scriptName,
                gamesNumber,
                wonGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber
        );
    }
}
