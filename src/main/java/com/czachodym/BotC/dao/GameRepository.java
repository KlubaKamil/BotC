package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.BotCJpaRepository;
import com.czachodym.BotC.dto.headers.GameHeader;
import com.czachodym.BotC.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends BotCJpaRepository<Game, Long> {
    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.GameHeader(
                g.id,
                s.name,
                function('group_concat', p.name),
                SIZE(g.assignments),
                g.goodWon,
                g.date)
            FROM Game g
            LEFT JOIN g.script s
            LEFT JOIN g.storytellers p
            JOIN g.groups gr
            WHERE gr.id = :groupId
            GROUP BY g.id, s.name, SIZE(g.assignments), g.goodWon, g.date
        """)
    List<GameHeader> findAllGameHeaders(long groupId);
}
