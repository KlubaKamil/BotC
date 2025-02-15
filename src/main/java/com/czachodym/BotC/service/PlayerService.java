package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.PlayerRepository;
import com.czachodym.BotC.dto.PlayerDto;
import com.czachodym.BotC.model.Player;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.throwIfExistsByName;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final DtoMapper dtoMapper;

    public PlayerDto getPlayer(long id){
        log.info("Checking if player exists.");
        Player player = throwIfNotFoundById(id, playerRepository);
        log.info("Player found.");
        return dtoMapper.mapPlayer(player);
    }

    public List<PlayerDto> getAllPlayers(){
        log.info("Getting all assignments");
        List<Player> players = playerRepository.findAll();
        log.info("Players found.");
        return dtoMapper.mapPlayerList(players);
    }

    public long createPlayer(PlayerDto playerDto){
        String name = playerDto.name();
        log.info("Checking if player exists.");
        throwIfExistsByName(name, playerRepository);
        Player player = buildPlayer(playerDto);
        log.info("Saving new player.");
        Player savedPlayer = playerRepository.save(player);
        long id = savedPlayer.getId();
        log.info("Player saved: {}.", id);

        return id;
    }

    public long editPlayer(PlayerDto playerDto){
        long id = playerDto.id();
        String name = playerDto.name();
        log.info("Checking if player exists.");
        Player player = throwIfNotFoundById(id, playerRepository);
        throwIfExistsByName(name, playerRepository);
        log.info("Player found, updating.");
        Player savedPlayer = buildPlayer(playerDto, player);
        playerRepository.save(savedPlayer);
        log.info("Player updated. Id: {}", id);

        return id;
    }

    @Transactional
    public boolean deletePlayer(long id){
        log.info("Deleting a player.");
        boolean exists = playerRepository.existsById(id);
        playerRepository.deleteById(id);
        boolean deleted = exists & !playerRepository.existsById(id);
        log.info("Deleted: {}", deleted);

        return deleted;
    }

    private Player buildPlayer(PlayerDto playerDto){
        return buildPlayer(playerDto, Player.builder());
    }

    private Player buildPlayer(PlayerDto playerDto, Player player){
        Player.PlayerBuilder<?,?> playerBuilder = player.toBuilder()
                .id(player.getId());
        return buildPlayer(playerDto, playerBuilder);
    }

    private Player buildPlayer(PlayerDto playerDto, Player.PlayerBuilder<?,?> builder){
        return builder
                .name(playerDto.name())
                .build();
    }
}
