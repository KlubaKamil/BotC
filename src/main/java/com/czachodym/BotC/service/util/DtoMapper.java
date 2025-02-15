package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.dto.util.PlayerCharacterPairDto;
import com.czachodym.BotC.model.Game;
import com.czachodym.BotC.model.Player;
import com.czachodym.BotC.model.Script;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.util.PlayerCharacterPair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {
    public ScriptDto mapScript(Script script){
        return ScriptDto.builder()
                .id(script.getId())
                .name(script.getName())
                .timesPlayed(script.getTimesPlayed())
                .characters(mapCharacterList(script.getCharacters()))
                .build();
    }

    public List<ScriptDto> mapScriptList(List<Script> scripts){
        return scripts.stream()
                .map(this::mapScript)
                .toList();
    }

    public CharacterDto mapCharacter(Character character){
        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .alignment(character.getAlignment())
                .good(character.isGood())
                .description(character.getDescription())
                .linkToWiki(character.getLinkToWiki())
                .build();
    }

    public List<CharacterDto> mapCharacterList(List<Character> characters){
        return characters.stream()
                .map(this::mapCharacter)
                .toList();
    }


    public GameDto mapGame(Game game){
        return GameDto.builder()
                .id(game.getId())
                .script(mapScript(game.getScript()))
                .storyteller(mapPlayer(game.getStoryteller()))
                .assignments(mapPlayerCharacterPairList(game.getAssignments()))
                .goodWon(game.isGoodWon())
                .date(game.getDate())
                .build();
    }

    public List<GameDto> mapGameList(List<Game> games){
        return games.stream()
                .map(this::mapGame)
                .toList();
    }

    public PlayerDto mapPlayer(Player player){
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .gamesNumber(player.getGamesNumber())
                .winRatio(player.getWinRatio())
                .goodPercentage(player.getGoodPercentage())
                .build();
    }

    public List<PlayerDto> mapPlayerList(List<Player> players){
        return players.stream()
                .map(this::mapPlayer)
                .toList();
    }

    public PlayerCharacterPairDto mapPlayerCharacterPair(PlayerCharacterPair playerCharacterPair){
        return PlayerCharacterPairDto.builder()
                .character(mapCharacter(playerCharacterPair.getCharacter()))
                .player(mapPlayer(playerCharacterPair.getPlayer()))
                .build();
    }

    public List<PlayerCharacterPairDto> mapPlayerCharacterPairList(List<PlayerCharacterPair> playerCharacterPairs){
        return playerCharacterPairs.stream()
                .map(this::mapPlayerCharacterPair)
                .toList();
    }
}
