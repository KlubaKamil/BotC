package com.czachodym.BotC.dto.details.player;

public record PlayerCharacterDetails(
    String characterName,
    long gamesNumber,
    long wonGamesNumber,
    double winRatio
){
    public PlayerCharacterDetails(String characterName, long gamesNumber, long wonGamesNumber) {
        this(
            characterName,
            gamesNumber,
            wonGamesNumber,
            gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber
        );
    }
}
