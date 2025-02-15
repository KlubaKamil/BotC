package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dao.GameRepository;
import com.czachodym.BotC.dao.PlayerRepository;
import com.czachodym.BotC.dao.ScriptRepository;
import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.util.PlayerCharacterPairDto;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Game;
import com.czachodym.BotC.model.Player;
import com.czachodym.BotC.model.Script;
import com.czachodym.BotC.model.util.PlayerCharacterPair;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.findEntities;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final ScriptRepository scriptRepository;
    private final PlayerRepository playerRepository;
    private final CharacterRepository characterRepository;
    private final DtoMapper dtoMapper;

    public GameDto getGame(long id){
        log.info("Checking if game exists.");
        Game game = throwIfNotFoundById(id, gameRepository);
        log.info("Game found.");
        log.info("ASSIGNMENTS: {}", game.getAssignments());
        log.info("MAPPED: {}", dtoMapper.mapGame(game).assignments());
        return dtoMapper.mapGame(game);
    }

    public List<GameDto> getAllGames(){
        log.info("Getting all games");
        List<Game> games = gameRepository.findAll();
        log.info("Games found.");
        return dtoMapper.mapGameList(games);
    }

    public long createGame(GameDto gameDto){
        Game game = buildGame(gameDto);
        log.info("Saving a new game.");
        log.info("RECEIVED: {}", gameDto.assignments());
        Game savedGame = gameRepository.save(game);
        log.info("SAVED: {}", savedGame.getAssignments());
        long id = savedGame.getId();
        log.info("Game saved. Id: {}", id);

        return game.getId();
    }

    public long editGame(GameDto gameDto){
        long id = gameDto.id();
        log.info("Checking if game exists.");
        Game game = throwIfNotFoundById(id, gameRepository);
        log.info("Game found, updating.");
        Game updatedGame = buildGame(gameDto, game);
        log.info("Updating a game.");
        gameRepository.save(updatedGame);
        log.info("Game updated. Id: {}", id);

        return id;
    }

    @Transactional
    public boolean deleteGame(long id){
        log.info("Deleting a game: {}", id);
        boolean exists = gameRepository.existsById(id);
        gameRepository.deleteById(id);
        boolean deleted = exists & !gameRepository.existsById(id);
        log.info("Deleted: {}", deleted);

        return deleted;
    }

    private Game buildGame(GameDto gameDto){
        return buildGame(gameDto, Game.builder());
    }

    private Game buildGame(GameDto gameDto, Game game){
        Game.GameBuilder gameBuilder = game.toBuilder()
                .id(gameDto.id());
        return buildGame(gameDto, gameBuilder);
    }

    private Game buildGame(GameDto gameDto, Game.GameBuilder builder){
        log.info("Validating a gameDto");
        long scriptId = gameDto.script().id();
        log.info("Looking for script: {}", scriptId);
        Script script = throwIfNotFoundById(scriptId, scriptRepository);

        long storytellerId = gameDto.storyteller().id();
        log.info("Script found, looking for storyteller: {}", storytellerId);
        Player storyTeller = throwIfNotFoundById(storytellerId, playerRepository);

        List<PlayerCharacterPairDto> playerCharacterPairDtos = gameDto.assignments();
        List<Long> playersIds = new ArrayList<>(playerCharacterPairDtos.size());
        List<Long> charactersIds = new ArrayList<>(playerCharacterPairDtos.size());
        playerCharacterPairDtos.forEach(pair -> {
            playersIds.add(pair.player().id());
            charactersIds.add(pair.character().id());
        });
        log.info("Storyteller found, looking for assignments: {}", playersIds);
        List<Player> players = findEntities(playersIds, playerRepository);
        log.info("Players found, looking for characters: {}", charactersIds);
        List<Character> characters = findEntities(charactersIds, characterRepository);

        List<PlayerCharacterPair> assignments = new ArrayList<>(playerCharacterPairDtos.size());
        playerCharacterPairDtos.forEach(pair -> {
            long playerId = pair.player().id();
            long characterId = pair.character().id();
            Player player = players.stream()
                    .filter(p -> playerId == p.getId()).findFirst().orElseThrow();
            Character character = characters.stream()
                    .filter(c -> characterId == c.getId()).findFirst().orElseThrow();
            PlayerCharacterPair playerCharacterPair = PlayerCharacterPair.builder()
                    .player(player)
                    .character(character)
                    .build();
            assignments.add(playerCharacterPair);
        });
        log.info("Validation successful, building a game.");

        return Game.builder()
                .storyteller(storyTeller)
                .script(script)
                .assignments(assignments)
                .goodWon(gameDto.goodWon())
                .date(gameDto.date())
                .build();
    }
}
