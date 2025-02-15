package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
//    int deleteById(long id);
}
