package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.*;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.GameDto;
import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.dto.util.AssignmentDto;
import com.czachodym.BotC.dto.util.BalanceMarkDto;
import com.czachodym.BotC.dto.util.TransformationDto;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.*;
import com.czachodym.BotC.model.util.Assignment;
import com.czachodym.BotC.model.util.BalanceMark;
import com.czachodym.BotC.model.util.CurrentUser;
import com.czachodym.BotC.model.util.Transformation;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.NotificationMode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final String IMAGES_DIR = "games";
    private final GameRepository gameRepository;
    private final ScriptRepository scriptRepository;
    private final PlayerRepository playerRepository;
    private final CharacterRepository characterRepository;
    private final PlaceRepository placeRepository;
    private final DtoMapper dtoMapper;
    private final Path root = Paths.get(IMAGES_DIR);
    private final Validators validators;
    private final CurrentUser currentUser;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(root);
    }

    public GameDto getGame(long id, long groupId) {
        log.info("Checking if game exists.");
        Game game = validators.throwIfNotFoundByIdAndGroupId(id, groupId, gameRepository);
        log.info("Game found.");
        return dtoMapper.mapGame(game);
    }

    public List<GameDto> getAllGames(long groupId) {
        log.info("Getting all games");
        List<Game> games = gameRepository.findByGroups_Id(groupId);
        log.info("Games found.");
        return dtoMapper.mapGameList(games);
    }

    public List<GameHeader> getAllGameHeaders(long groupId) {
        log.info("Getting all game headers");
        List<GameHeader> gameHeaders = gameRepository.findAllGameHeaders(groupId);
        log.info("Headers found.");
        return gameHeaders;
    }

    public long createGame(long groupId, GameDto gameDto) {
        Game game = buildGame(groupId, gameDto);
        log.info("Saving a new game.");
        Game savedGame = gameRepository.save(game);
        long id = savedGame.getId();
        log.info("Game saved. Id: {}", id);

        return game.getId();
    }

    public long editGame(long groupId, GameDto gameDto) {
        long id = gameDto.id();
        log.info("Checking if game exists.");
        Game game = validators.throwIfNotFoundByIdAndGroupId(id, groupId, gameRepository);
        log.info("Game found, updating.");
        Game updatedGame = buildGame(groupId, gameDto, game);
        log.info("Updating a game.");
        gameRepository.save(updatedGame);
        log.info("Game updated. Id: {}", id);
        return id;
    }

    @Transactional
    public void deleteGame(long id, long groupId) {
        log.info("Deleting a game: {}", id);

        log.info("Checking if group available: {}.", groupId);
        validators.throwIfGroupNotAvailableMod(groupId);
        log.info("Checking if game exists: {}.", id);
        validators.throwIfNotFoundByIdAndGroupId(id, groupId, gameRepository);
        gameRepository.deleteById(id);

        boolean deleted = gameRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    public boolean uploadImage(long id, long groupId, List<MultipartFile> images) {
        try{
            log.info("Checking if game exists.");
            Game game = validators.throwIfNotFoundByIdAndGroupId(id, groupId, gameRepository);
            Path gameDir = root.resolve("game_" + id);
            Files.createDirectories(gameDir);

            for(MultipartFile image : images) {
                String filename = UUID.randomUUID().toString();
                Path filePath = gameDir.resolve(filename);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            game.setImageUploaded(true);
            gameRepository.save(game);
            log.info("Images saved.");
            return true;
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            return false;
        }
    }

    public List<String> getImageNames(long id, long groupId) {
        Path gameDir = root.resolve("game_" + id);
        List<String> resources = new ArrayList();
        if (!Files.exists(gameDir)) {
            return resources;
        } else {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(gameDir)) {
                for(Path path : stream) {
                    resources.add((new UrlResource(path.toUri())).getFilename());
                }
            } catch (IOException e) {
                log.error("Failed to load images", e);
            }

            return resources;
        }
    }

    public Resource getImage(long id, long groupId, String filename) {
        Path filePath = root.resolve("game_" + id).resolve(filename);

        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public boolean deleteImages(long id, long groupId, List<String> names) {
        Game game = validators.throwIfNotFoundByIdAndGroupId(id, groupId, gameRepository);
        Path gameDir = root.resolve("game_" + id);

        try {
            if (Files.exists(gameDir)) {
                Files.walk(gameDir).sorted(Comparator.reverseOrder()).map(Path::toFile).filter(f -> names.contains(f.getName())).forEach(File::delete);
                log.info("Images deleted for game " + id);
                Stream<Path> stream = Files.list(gameDir);
                boolean hasFiles = stream.findAny().isPresent();
                if (!hasFiles) {
                    game.setImageUploaded(false);
                    gameRepository.save(game);
                }
            }
        } catch (IOException e) {
            log.error("Failed to delete images", e);
            return false;
        }
        return true;
    }

    public String addBalanceMark(long groupId, long gameId, BalanceMarkDto balanceMarkDto) {
        Game game = validateBalanceMark(groupId, gameId, balanceMarkDto.username());
        Optional<BalanceMark> balanceMarkOptional = game.getBalanceMarks().stream()
                .filter(bm -> bm.getUsername().equals(balanceMarkDto.username()))
                .findFirst();
        if(balanceMarkOptional.isPresent()){
            log.info("Balance mark already exists, updating...");
            balanceMarkOptional.get().setMark(balanceMarkDto.mark());
        } else {
            log.info("Balance mark not exist, adding new one");
            game.getBalanceMarks().add(BalanceMark.builder()
                    .username(balanceMarkDto.username())
                    .mark(balanceMarkDto.mark())
                    .build());
        }
        gameRepository.save(game);
        log.info("Balance mark added successfully.");
        return balanceMarkDto.username();
    }

    public void deleteBalanceMark(long groupId, long gameId, String username){
        Game game = validateBalanceMark(groupId, gameId, username);
        Optional<BalanceMark> balanceMarkOptional = game.getBalanceMarks().stream()
                .filter(bm -> bm.getUsername().equals(username))
                .findFirst();
        log.info("Game found, deleting balance mark...");
        balanceMarkOptional.ifPresent(balanceMark -> game.getBalanceMarks().remove(balanceMark));
        gameRepository.save(game);
        log.info("Balance mark deleted successfully.");
    }

    public String getMessage(long id, NotificationMode notificationMode){
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nową grę!" : "Edytowano grę!";
        Game game = validators.throwIfNotFoundByIdAndGroupId(id, 0, gameRepository);
        String script = game.getScript().getName();
        String storyteller = getStorytellers(game.getStorytellers());
        String fabled = getFables(game.getFables());
        String goodWon = game.isGoodWon() ? "Dobro" : "Zło";
        String date = game.getDate() == null ? "-" : game.getDate().toString();
        String place = validators.getIfBotCEntityNotNull(game.getPlace(), "-");
        String balance = getBalance(game.getBalanceMarks());
        String assignments = getAssignments(game.getAssignments());
        String notes = validators.getIfNotNull(game.getNotes(), "-");
        return """
                %s
                Id: %d
                Skrypt: %s
                Narrator: %s
                Legenda: %s
                Zwycięzcy: %s
                Data: %s
                Lokalizacja: %s
                Balans: %s
                Lista graczy i postaci:
                %s
                Notatki: 
                %s
                Kliknij i zobacz: %s/games/%d
                """.formatted(modeMessage, id, script, storyteller, fabled, goodWon, date, place, balance, assignments,
                    notes, FRONTEND_URL, id);
    }

    private String getBalance(Set<BalanceMark> balanceMarks){
        if(balanceMarks == null || balanceMarks.size() == 0) return "-";
        double average = balanceMarks.stream()
                .mapToInt(BalanceMark::getMark)
                .average()
                .orElse(0);
        average = Math.round(average * 10.0) / 10.0;
        String balance = average + " [";
        balance += String.join(", ", balanceMarks.stream().map(Object::toString).toList());
        balance += "]; ";
        balance += balanceMarks.size();
        balance += " ocen.";
        return balance;
    }

    private String getStorytellers(List<Player> storytellers) {
        if (storytellers.size() == 0) {
            return "";
        } else {
            String message = storytellers.get(0).getName();

            for(int i = 1; i < storytellers.size(); ++i) {
                Player storyteller = storytellers.get(i);
                message = message + ", " + storyteller.getName();
            }

            return message;
        }
    }

    private String getFables(List<Character> fables){
        if (fables.size() == 0){
            return "";
        }
        String message = fables.get(0).getName();
        for(int i = 1; i < fables.size(); i++){
            Character fable = fables.get(i);
            message += ", " + fable.getName();
        }
        return message;
    }

    private String getAssignments(List<Assignment> assignments){
        String message = "";
        for(Assignment a: assignments){
            message += "            " + a.getPlayer().getName() + ": " + a.getCharacter().getName() + " - " +
                    getGood(a.isGood()) + "\n";
            for(Transformation t: a.getTransformations()){
                message += "                        Zmiana w: " + t.getCharacter().getName() + " - " +
                        getGood(t.isGood()) + "\n";
            }
        }
        return message;
    }

    private String getGood(boolean isGood){
        return isGood ? "Dobro" : "Zło";
    }

    private Game validateBalanceMark(long groupId, long gameId, String username){
        log.info("Checking if username is correct: {}", username);
        if(!currentUser.getUsername().equals(username)){
            throw new IllegalArgumentException();
        }
        log.info("Username correct, checking if group is available: {}", groupId);
        validators.throwIfGroupNotAvailableMember(groupId);
        log.info("Group available, getting a game: {}", gameId);
        return validators.throwIfNotFoundByIdAndGroupId(gameId, groupId, gameRepository);
    }

    private Game buildGame(long groupId, GameDto gameDto){
        return buildGame(groupId, gameDto, new Game());
    }

    private Game buildGame(long groupId, GameDto gameDto, Game game){
        log.info("Validating a gameDto");

        Set<Group> groups = game.getGroups();
        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        groups.add(group);

        long scriptId = gameDto.script().id();
        log.info("Group available, looking for script: {}", scriptId);
        Script script = validators.throwIfNotFoundByIdAndGroupId(scriptId, groupId, scriptRepository);

        List<PlayerDto> storytellerDtos = gameDto.storytellers();
        List<Long> storytellerIds = storytellerDtos.stream().map(PlayerDto::id).toList();
        log.info("Script found, looking for storytellers: {}", storytellerIds);
        List<Player> storytellers = validators.throwIfEntitiesNotExistByGroupId(storytellerIds, groupId, playerRepository);
        log.info("Storytellers found, checking if fables selected.");

        List<CharacterDto> fableDtos = gameDto.fables();
        List<Character> fables = new ArrayList<>();
        if(fableDtos == null || fableDtos.size() == 0){
            log.info("Game without fables, skipping.");
        } else {
            List<Long> ids = fableDtos.stream()
                    .map(CharacterDto::id)
                    .toList();
            fables = validators.throwIfEntitiesNotExistByGroupId(ids, groupId, characterRepository);
            log.info("Fables found.");
        }

        List<Assignment> assignments = buildAssignments(gameDto.assignments(), groupId);

        PlaceDto placeDto = gameDto.place();
        Place place = null;
        log.info("Characters found, checking if place selected.");
        if(placeDto == null){
            log.info("Game without place, skipping.");
        } else {
            long id = placeDto.id();
            log.info("Game with place, looking for id: {}", id);
            place = validators.throwIfNotFoundByIdAndGroupId(id, groupId, placeRepository);
            log.info("Place found.");
        }

        log.info("Validation successful, building a game.");

        return game.toBuilder()
                .groups(groups)
                .script(script)
                .storytellers(storytellers)
                .fables(fables)
                .assignments(assignments)
                .goodWon(gameDto.goodWon())
                .date(gameDto.date())
                .notes(gameDto.notes())
                .place(place)
                .imageUploaded(gameDto.imageUploaded())
                .balanceMarks(game.getBalanceMarks())
                .build();
    }

    private List<Assignment> buildAssignments(List<AssignmentDto> assignmentDtos, long groupId){
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
        List<Player> players = validators.throwIfEntitiesNotExistByGroupId(playersIds, groupId, playerRepository);
        log.info("Players found, looking for characters: {}", charactersIds);
        List<Character> characters = validators.throwIfEntitiesNotExistByGroupId(charactersIds, groupId, characterRepository);
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
                            .type(t.type())
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
