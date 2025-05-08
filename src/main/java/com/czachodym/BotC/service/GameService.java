package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.*;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.dto.util.AssignmentDto;
import com.czachodym.BotC.dto.util.TransformationDto;
import com.czachodym.BotC.model.*;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.util.Assignment;
import com.czachodym.BotC.model.util.Transformation;
import com.czachodym.BotC.service.util.DtoMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.findEntitiesById;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final String IMAGES_DIR = "games";
    private final GameRepository gameRepository;
    private final ScriptRepository scriptRepository;
    private final PlayerRepository playerRepository;
    private final CharacterRepository characterRepository;
    private final PlaceRepository placeRepository;
    private final DtoMapper dtoMapper;
    private final Path root = Paths.get(IMAGES_DIR);

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(root);
    }

    public GameDto getGame(long id) {
        log.info("Checking if game exists.");
        Game game = throwIfNotFoundById(id, gameRepository);
        log.info("Game found.");
        return dtoMapper.mapGame(game);
    }

    public List<GameDto> getAllGames() {
        log.info("Getting all games");
        List<Game> games = gameRepository.findAll();
        log.info("Games found.");
        return dtoMapper.mapGameList(games);
    }

    public List<GameHeader> getAllGameHeaders() {
        log.info("Getting all game headers");
        List<GameHeader> gameHeaders = gameRepository.findAllGameHeaders();
        log.info("Headers found.");
        return gameHeaders;
    }

    public long createGame(GameDto gameDto) {
        Game game = buildGame(gameDto);
        log.info("Saving a new game.");
        Game savedGame = gameRepository.save(game);
        long id = savedGame.getId();
        log.info("Game saved. Id: {}", id);

        return game.getId();
    }

    public long editGame(GameDto gameDto) {
        long id = gameDto.id();
        log.info("Checking if game exists.");
        Game game = throwIfNotFoundById(id, gameRepository);
        log.info("Game found, updating.");
        Game updatedGame = buildGame(gameDto, game);
        log.info("Updating a game.");
        gameRepository.save(updatedGame);
        log.info("Game updated. Id: {}", id);
        String imageUrl = gameDto.imageUrl();
        if(imageUrl == null){
            log.info("Trying to delete image.");
            deleteImage(gameDto.id());
        }
        return id;
    }

    @Transactional
    public void deleteGame(long id) {
        log.info("Deleting a game: {}", id);
        boolean exists = gameRepository.existsById(id);
        gameRepository.deleteById(id);
        boolean deleted = exists & !gameRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    public String uploadImage(long id, MultipartFile image) {
        try{
            log.info("Checking if game exists.");
            Game game = throwIfNotFoundById(id, gameRepository);
            log.info("Game found, saving.");
            String filename = "game_" + id + ".jpg";
            Path filePath = root.resolve(filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String url = "game/" + id + "/image";
            game.setImageUrl(url);
            gameRepository.save(game);
            log.info("Image saved.");
            return url;
        } catch (IOException e) {
            return null;
        }
    }

    public Resource getImage(long id){
        Game game = throwIfNotFoundById(id, gameRepository);
        if (game.getImageUrl() == null) {
            return null;
        }

        Path filePath = root.resolve(Paths.get("game_" + id + ".jpg"));
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }


    private void deleteImage(long id) {
        Path filePath = root.resolve(Paths.get("game_" + id + ".jpg"));
        try {
            Files.delete(filePath);
            log.info("Image deleted.");
        } catch (IOException e) {
            log.info("No image found.");
        }
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

        CharacterDto fabledDto = gameDto.fabled();
        Character fabled = null;
        log.info("Storyteller found, checking if fabled selected.");
        if(fabledDto == null){
            log.info("Game without fabled, skipping.");
        } else {
            long id = fabledDto.id();
            log.info("Game with fabled, looking for id: {}", id);
            fabled = throwIfNotFoundById(id, characterRepository);
            log.info("Fabled found.");
        }
        List<Assignment> assignments = buildAssignments(gameDto.assignments());
        PlaceDto placeDto = gameDto.place();
        Place place = null;
        log.info("Characters found, checking if place selected.");
        if(placeDto == null){
            log.info("Game without place, skipping.");
        } else {
            long id = placeDto.id();
            log.info("Game with place, looking for id: {}", id);
            place = throwIfNotFoundById(id, placeRepository);
            log.info("Place found.");
        }
        log.info("Validation successful, building a game.");

        return builder
                .script(script)
                .storyteller(storyTeller)
                .fabled(fabled)
                .assignments(assignments)
                .goodWon(gameDto.goodWon())
                .date(gameDto.date())
                .notes(gameDto.notes())
                .place(place)
                .imageUrl(gameDto.imageUrl())
                .balanceMarks(gameDto.balanceMarks())
                .build();
    }

    private List<Assignment> buildAssignments(List<AssignmentDto> assignmentDtos){
        List<Long> playersIds = new ArrayList<>(assignmentDtos.size());
        List<Long> charactersIds = new ArrayList<>(assignmentDtos.size());
        assignmentDtos.forEach(a -> {
            playersIds.add(a.player().id());
            charactersIds.add(a.character().id());
            charactersIds.addAll(a.transformations().stream()
                .map(t -> t.character().id())
                .toList());
        });
        log.info("Looking for players: {}", playersIds);
        List<Player> players = findEntitiesById(playersIds, playerRepository);
        log.info("Players found, looking for characters: {}", charactersIds);
        List<Character> characters = findEntitiesById(charactersIds, characterRepository);
        return assignmentDtos.stream()
            .map(a -> {
                long playerDtoId = a.player().id();
                long characterDtoId = a.character().id();
                int index = a.index();
                boolean good = a.good();
                List<TransformationDto> transformationDtos = a.transformations();
                Player player = players.stream()
                    .filter(p -> playerDtoId == p.getId()).findFirst().orElseThrow();
                Character character = characters.stream()
                    .filter(c -> characterDtoId == c.getId()).findFirst().orElseThrow();
                List<Transformation> transformations = transformationDtos.stream()
                    .map(t -> {
                        long transformedCharacterDtoId = t.character().id();
                        Character transformedCharacter = characters.stream()
                            .filter(c -> transformedCharacterDtoId == c.getId()).findFirst().orElseThrow();
                        boolean transformedGood = t.good();
                        return Transformation.builder()
                            .character(transformedCharacter)
                            .good(transformedGood)
                            .build();
                    })
                    .toList();
                return Assignment.builder()
                    .player(player)
                    .character(character)
                    .assignmentIndex(index)
                    .good(good)
                    .transformations(transformations)
                    .build();
            })
            .sorted(Comparator.comparingInt(a -> a.getCharacter().getAlignment().ordinal()))
            .toList();
    }
}
