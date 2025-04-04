package com.czachodym.BotC.dto.details.script;

public record ScriptCharacterDetails(
  long characterId,
  String name,
  long gamesNumber,
  double occurrencePercentage,
  long wonGamesNumber,
  double winRatio
){
    public ScriptCharacterDetails(long characterId, String name, long gamesNumber, long totalScriptNumber, long wonGamesNumber) {
        this(
            characterId,
            name,
            gamesNumber,
            totalScriptNumber == 0 ? 0 : 100.0 * gamesNumber / totalScriptNumber,
            wonGamesNumber,
            gamesNumber == 0 ? 0 : 100.0 * wonGamesNumber / gamesNumber
        );
    }
}
