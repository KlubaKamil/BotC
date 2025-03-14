package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dto.*;
import com.czachodym.BotC.dto.util.AssignmentDto;
import com.czachodym.BotC.dto.util.TransformationDto;
import com.czachodym.BotC.model.*;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.util.Assignment;
import com.czachodym.BotC.model.util.Transformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DtoMapper {
    public ScriptDto mapScript(Script script){
        return ScriptDto.builder()
                .id(script.getId())
                .name(script.getName())
                .characters(mapCharacterList(script.getCharacters()))
                .build();
    }

    public List<ScriptDto> mapScriptList(List<Script> scripts){
        return scripts.stream()
                .map(this::mapScript)
                .toList();
    }

    public CharacterDto mapCharacter(Character character){
        return character == null ? null :
                CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .maxStartNumber(character.getMaxStartNumber())
                .alignment(character.getAlignment())
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
                .fabled(mapCharacter(game.getFabled()))
                .assignments(mapAssignments(game.getAssignments()))
                .goodWon(game.isGoodWon())
                .date(game.getDate())
                .notes(game.getNotes())
                .place(mapPlace(game.getPlace()))
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
                .transformations(mapTransformations(assignment.getTransformations()))
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

    public List<TransformationDto> mapTransformations(List<Transformation> transformations){
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
}
