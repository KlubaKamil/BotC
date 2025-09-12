package com.czachodym.BotC.dto.details.player;

public record PlayerScriptDetails (
        long scriptId,
        String scriptName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public PlayerScriptDetails(long scriptId, String scriptName, long gamesNumber, long wonGamesNumber) {
        this(
                scriptId,
                scriptName,
                gamesNumber,
                wonGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber);
    }
}
