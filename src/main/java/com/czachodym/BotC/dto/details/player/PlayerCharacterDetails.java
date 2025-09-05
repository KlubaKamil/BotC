package com.czachodym.BotC.dto.details.player;

public record PlayerCharacterDetails(
        long characterId,
        String characterName,
        long gamesNumber,
        long wonGamesNumber,
        double winRatio
){
    public PlayerCharacterDetails(long id, String characterName, long gamesNumber, long wonGamesNumber) {
        this(
                id,
                characterName,
                gamesNumber,
                wonGamesNumber,
                gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber
        );
    }
}
