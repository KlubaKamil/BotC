package com.czachodym.BotC.dto.details.player;

public record PlayerScriptDetails (
        String scriptName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public PlayerScriptDetails(String scriptName, long gamesNumber, long wonGamesNumber) {
        this(
                scriptName,
                gamesNumber,
                wonGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber);
    }
}
