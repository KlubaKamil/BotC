package com.czachodym.BotC.dto.details.player;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record PlayerDetails (
    long storytellerGamesNumber,
    long gamesNumber,
    double goodPercentage,
    double winRatio,
    List<PlayerCharacterDetails> playerCharactersDetails,
    List<PlayerScriptDetails> playerScriptsDetails
){
    PlayerDetails(long storytellerGamesNumber, long gamesNumber, long gamesBeingGood, long wonGames){
        this(
                storytellerGamesNumber,
                gamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * gamesBeingGood / gamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGames / gamesNumber,
                null,
                null);
    }
}
