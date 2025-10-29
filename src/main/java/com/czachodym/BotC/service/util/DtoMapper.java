package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dto.*;
import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.util.*;
import com.czachodym.BotC.model.*;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DtoMapper {
    public ScriptDto mapScript(Script script){
        return mapScript(script, null);
    }

    public ScriptDto mapScript(Script script, ScriptDetails scriptDetails){
        return ScriptDto.builder()
                .id(script.getId())
                .groups(script.getGroups())
                .name(script.getName())
                .author(script.getAuthor())
                .notes(script.getNotes())
                .scriptCharacters(mapScriptCharacterList(script.getScriptCharacters()))
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
                .groups(character.getGroups())
                .name(character.getName())
                .maxStartNumber(character.getMaxStartNumber())
                .alignment(character.getAlignment())
                .description(character.getDescription())
                .linkToWiki(character.getLinkToWiki())
                .tips(character.getTips())
                .imageUploaded(character.isImageUploaded())
                .characterDetails(characterDetails)
                .build();
    }

    public List<CharacterDto> mapCharacterList(List<Character> characters){
        return characters.stream()
                .map(this::mapCharacter)
                .toList();
    }

    public ScriptCharacterDto mapScriptCharacter(ScriptCharacter scriptCharacter){
        return ScriptCharacterDto.builder()
                .character(mapCharacter(scriptCharacter.getCharacter()))
                .characterOrder(scriptCharacter.getCharacterOrder())
                .build();
    }

    public List<ScriptCharacterDto> mapScriptCharacterList(List<ScriptCharacter> scriptCharacters){
        return scriptCharacters.stream()
                .map(this::mapScriptCharacter)
                .toList();
    }

    public GameDto mapGame(Game game){
        return GameDto.builder()
                .id(game.getId())
                .groups(game.getGroups())
                .script(mapScript(game.getScript()))
                .storytellers(this.mapPlayerList(game.getStorytellers()))
                .fables(mapCharacterList(game.getFables()))
                .assignments(mapAssignmentList(game.getAssignments()))
                .goodWon(game.isGoodWon())
                .date(game.getDate())
                .notes(game.getNotes())
                .place(mapPlace(game.getPlace()))
                .imageUploaded(game.isImageUploaded())
                .balanceMarks(mapBalanceMarkSet(game.getBalanceMarks()))
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
                .groups(player.getGroups())
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

    public List<AssignmentDto> mapAssignmentList(List<Assignment> assignments){
        return assignments.stream()
                .map(this::mapAssignment)
                .toList();
    }

    public TransformationDto mapTransformation(Transformation transformation){
        return TransformationDto.builder()
                .character(mapCharacter(transformation.getCharacter()))
                .good(transformation.isGood())
                .type(transformation.getType())
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
                .groups(place.getGroups())
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
                .groups(achievement.getGroups())
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

    public UserDto mapUser(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .groupRoles(user.getGroupRoles())
                .build();
    }

    public List<UserDto> mapUserList(List<User> users){
        return users.stream()
                .map(this::mapUser)
                .toList();
    }

    public GroupDto mapGroup(Group group){
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }

    public List<GroupDto> mapGroup(List<Group> groups){
        return groups.stream()
                .map(this::mapGroup)
                .toList();
    }

    public BalanceMarkDto mapBalanceMark(BalanceMark balanceMark){
        return BalanceMarkDto.builder()
                .username(balanceMark.getUsername())
                .mark(balanceMark.getMark())
                .build();
    }

    public Set<BalanceMarkDto> mapBalanceMarkSet(Set<BalanceMark> balanceMarks){
        return balanceMarks.stream()
                .map(this::mapBalanceMark)
                .collect(Collectors.toSet());
    }
}
