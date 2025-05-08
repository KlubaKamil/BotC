package com.czachodym.BotC.dto.headers;

public record PlayerHeader (
    long id,
    String name,
    String discordName,
    long gamesNumber,
    double goodPercentage,
    double winRatio
){
    public PlayerHeader(long id, String name, String discordName, long gamesNumber, long gamesBeingGood, long gamesWon) {
        this(
                id,
                name,
                discordName,
                gamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * gamesBeingGood / gamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * gamesWon / gamesNumber
        );
    }
}
