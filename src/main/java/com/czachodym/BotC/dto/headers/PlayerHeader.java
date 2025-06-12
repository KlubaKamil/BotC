package com.czachodym.BotC.dto.headers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record PlayerHeader (
    long id,
    String name,
    String discordName,
    long storytellerGamesNumber,
    long gamesNumber,
    double storytellerWinRatio,
    double goodPercentage,
    double winRatio
){
    public PlayerHeader(long id, String name, String discordName, long storytellerGamesNumber, long gamesNumber,
                        long storytellerGamesGoodWonNumber, long gamesBeingGood, long gamesWon) {
        this(
                id,
                name,
                discordName,
                storytellerGamesNumber,
                gamesNumber,
                storytellerGamesGoodWonNumber == 0 ? 0 : 100.0 * storytellerGamesGoodWonNumber / storytellerGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * gamesBeingGood / gamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * gamesWon / gamesNumber
        );
    }
}
