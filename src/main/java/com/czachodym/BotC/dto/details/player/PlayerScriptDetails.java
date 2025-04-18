package com.czachodym.BotC.dto.details.player;

public record PlayerScriptDetails (
        long id,
        String scriptName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public PlayerScriptDetails(long id, String scriptName, long gamesNumber, long wonGamesNumber) {
        this(
                id,
                scriptName,
                gamesNumber,
                wonGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber);
    }
}
