package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.dto.details.player.PlayerCharacterDetails;
import com.czachodym.BotC.dto.details.player.PlayerScriptDetails;
import com.czachodym.BotC.dto.headers.PlayerHeader;
import com.czachodym.BotC.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends NameJpaRepository<Player, Long> {
    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.PlayerHeader(
                p.id,
                p.name,
                COUNT(a.id),
                COUNT(DISTINCT CASE WHEN a.good = true THEN g.id ELSE NULL END), 
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            ) 
            FROM Player p
            LEFT JOIN Assignment a ON p.id = a.player.id
            LEFT JOIN Game g ON g.id = (
                SELECT g2.id FROM Game g2 
                JOIN g2.assignments a2 
                WHERE a2.id = a.id
            )
            GROUP BY p.id, p.name
        """)
    List<PlayerHeader> findAllPlayerHeaders();

    @Query("""      
            SELECT new com.czachodym.BotC.dto.details.player.PlayerCharacterDetails(
                c.name,
                COUNT(c.id),
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            )
            FROM Player p
            LEFT JOIN Assignment a on p = a.player
            LEFT JOIN Character c on c = a.character
            LEFT JOIN Game g ON g.id = (
                SELECT g2.id FROM Game g2
                JOIN g2.assignments a2 
                WHERE a2.id = a.id
            )
            WHERE a.player.id = :id
            GROUP BY c.name
        """)
    List<PlayerCharacterDetails> findPlayerCharacterDetailsById(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.player.PlayerScriptDetails(
                s.name,
                COUNT(s.id),
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            )
            FROM Player p
            LEFT JOIN Assignment a ON p = a.player
            LEFT JOIN Game g ON g.id = (
                SELECT g2.id FROM Game g2
                JOIN g2.assignments a2 
                WHERE a2.id = a.id
            )
            LEFT JOIN Script s ON g.script = s
            WHERE a.player.id = :id
            GROUP BY s.name
        """)
    List<PlayerScriptDetails> findPlayerScriptDetailsById(long id);
}
