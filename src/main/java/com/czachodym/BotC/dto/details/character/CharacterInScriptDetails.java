package com.czachodym.BotC.dto.details.character;

public record CharacterInScriptDetails (
        String scriptName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public CharacterInScriptDetails(String scriptName, long gamesNumber, long wonGamesNumber) {
        this(
            scriptName,
            gamesNumber,
            wonGamesNumber,
            gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber
        );
    }
}
