package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dto.*;
import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.util.AssignmentDto;
import com.czachodym.BotC.dto.util.PlayerAchievementDto;
import com.czachodym.BotC.dto.util.TransformationDto;
import com.czachodym.BotC.model.*;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.util.Assignment;
import com.czachodym.BotC.model.util.PlayerAchievement;
import com.czachodym.BotC.model.util.Transformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DtoMapper {
    public ScriptDto mapScript(Script script){
        return mapScript(script, null);
    }

    public ScriptDto mapScript(Script script, ScriptDetails scriptDetails){
        return ScriptDto.builder()
                .id(script.getId())
                .name(script.getName())
                .author(script.getAuthor())
                .notes(script.getNotes())
                .characters(mapCharacterList(script.getCharacters()))
                .scriptDetails(scriptDetails)
                .build();
    }

    public List<ScriptDto> mapScriptList(List<Script> scripts){
        return scripts.stream()
                .map(this::mapScript)
                .toList();
    }

    public CharacterDto mapCharacter(Character character){
        return mapCharacter(character, null);
    }

    public CharacterDto mapCharacter(Character character, CharacterDetails characterDetails){
        return character == null ? null :
                CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .maxStartNumber(character.getMaxStartNumber())
                .alignment(character.getAlignment())
                .description(character.getDescription())
                .linkToWiki(character.getLinkToWiki())
                .tips(character.getTips())
                .characterDetails(characterDetails)
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
                .fabled(mapCharacter(game.getFabled()))
                .assignments(mapAssignments(game.getAssignments()))
                .goodWon(game.isGoodWon())
                .date(game.getDate())
                .notes(game.getNotes())
                .place(mapPlace(game.getPlace()))
                .imageUrl(game.getImageUrl())
                .balanceMarks(game.getBalanceMarks())
                .build();
    }

    public List<GameDto> mapGameList(List<Game> games){
        return games.stream()
                .map(this::mapGame)
                .toList();
    }

    public PlayerDto mapPlayer(Player player) {
        return mapPlayer(player, null);
    }

    public PlayerDto mapPlayer(Player player, PlayerDetails playerDetails){
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .discordName(player.getDiscordName())
                .playerAchievements(mapPlayerAchievementList(player.getPlayerAchievements()))
                .playerDetails(playerDetails)
                .build();
    }

    public List<PlayerDto> mapPlayerList(List<Player> players){
        return players.stream()
                .map(this::mapPlayer)
                .toList();
    }

    public AssignmentDto mapAssignment(Assignment assignment){
        return AssignmentDto.builder()
                .character(mapCharacter(assignment.getCharacter()))
                .player(mapPlayer(assignment.getPlayer()))
                .index(assignment.getAssignmentIndex())
                .good(assignment.isGood())
                .transformations(mapTransformationList(assignment.getTransformations()))
                .build();
    }

    public List<AssignmentDto> mapAssignments(List<Assignment> assignments){
        return assignments.stream()
                .map(this::mapAssignment)
                .toList();
    }

    public TransformationDto mapTransformation(Transformation transformation){
        return TransformationDto.builder()
                .character(mapCharacter(transformation.getCharacter()))
                .good(transformation.isGood())
                .build();
    }

    public List<TransformationDto> mapTransformationList(List<Transformation> transformations){
        return transformations.stream()
                .map(this::mapTransformation)
                .toList();
    }

    public PlaceDto mapPlace(Place place){
        return place == null ? null :
                PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .build();
    }
    public List<PlaceDto> mapPlaceList(List<Place> places){
        return places.stream()
                .map(this::mapPlace)
                .toList();
    }

    public AchievementDto mapAchievement(Achievement achievement) {
        return mapAchievement(achievement, null);
    }

    public AchievementDto mapAchievement(Achievement achievement, AchievementDetails achievementDetails){
        return AchievementDto.builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .description(achievement.getDescription())
                .achievementDetails(achievementDetails)
                .build();
    }

    public List<AchievementDto> mapAchievementList(List<Achievement> achievements){
        return achievements.stream()
                .map(this::mapAchievement)
                .toList();
    }

    public PlayerAchievementDto mapPlayerAchievement(PlayerAchievement playerAchievement){
        return PlayerAchievementDto.builder()
                .id(playerAchievement.getId())
                .achievement(mapAchievement(playerAchievement.getAchievement()))
                .date(playerAchievement.getDate())
                .build();
    }

    public List<PlayerAchievementDto> mapPlayerAchievementList(List<PlayerAchievement> playerAchievements){
        return playerAchievements.stream()
                .map(this::mapPlayerAchievement)
                .toList();
    }


}
