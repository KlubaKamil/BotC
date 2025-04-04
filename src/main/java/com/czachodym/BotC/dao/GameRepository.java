package com.czachodym.BotC.dao;

import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.GameHeader(
                g.id,
                s.name,
                p.name,
                SIZE(g.assignments),
                g.goodWon,
                g.date)
            FROM Game g
            LEFT JOIN g.script s
            LEFT JOIN g.storyteller p
        """)
    List<GameHeader> findAllGameHeaders();
}
