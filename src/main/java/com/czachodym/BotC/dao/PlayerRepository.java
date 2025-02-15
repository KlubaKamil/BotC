package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends NameJpaRepository<Player, Long> {

}
