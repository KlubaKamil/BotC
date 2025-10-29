package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.BotCNameJpaRepository;
import com.czachodym.BotC.dto.details.player.PlayerCharacterDetails;
import com.czachodym.BotC.dto.details.player.PlayerDetails;
import com.czachodym.BotC.dto.details.player.PlayerScriptDetails;
import com.czachodym.BotC.dto.headers.PlayerHeader;
import com.czachodym.BotC.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends BotCNameJpaRepository<Player, Long> {
    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.PlayerHeader(
                p.id,
                p.name,
                p.discordName,
                COUNT(DISTINCT gs.id),
                COUNT(DISTINCT a.id),
                COUNT(DISTINCT CASE WHEN gs.goodWon = true THEN gs.id ELSE NULL END),
                COUNT(DISTINCT CASE WHEN
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                                AND t.type = 'BECOME'
                        ),
                        a.good
                    ) = true
                    THEN g.id ELSE NULL END),
                COUNT(DISTINCT CASE WHEN g.goodWon =
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                        ),
                        a.good
                    )
                    THEN g.id ELSE NULL END)
            )
            FROM Player p
            LEFT JOIN Assignment a ON p.id = a.player.id
            LEFT JOIN Game g ON g.id = (
                SELECT g2.id FROM Game g2
                JOIN g2.assignments a2
                WHERE a2.id = a.id
            )
            LEFT JOIN Game gs ON p MEMBER OF gs.storytellers
            JOIN p.groups gr
            WHERE gr.id = :groupId
            GROUP BY p.id, p.name, p.discordName
        """)
    List<PlayerHeader> findAllPlayerHeaders(long groupId);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.player.PlayerDetails(
                COUNT(DISTINCT gs.id),
                COUNT(DISTINCT g.id),
                COUNT(DISTINCT CASE WHEN
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                                AND t.type = 'BECOME'
                        ),
                        a.good
                    ) = true
                    THEN g.id ELSE NULL END),
                COUNT(DISTINCT CASE WHEN g.goodWon =
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                                AND t.type = 'BECOME'
                        ),
                        a.good
                    )
                    THEN g.id ELSE NULL END)
            )
            FROM Player p
            LEFT JOIN Assignment a ON p = a.player
            LEFT JOIN Game g ON g.id = (
                SELECT g2.id FROM Game g2
                JOIN g2.assignments a2
                WHERE a2.id = a.id
            )
            LEFT JOIN Game gs ON p MEMBER OF gs.storytellers
            WHERE p.id = :id
        """)
    PlayerDetails findPlayerDetails(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.player.PlayerCharacterDetails(
                c.id,
                c.name,
                COUNT(c.id),
                COUNT(DISTINCT CASE WHEN g.goodWon =
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                                AND t.type = 'BECOME'
                        ),
                        a.good
                    )
                    THEN g.id ELSE NULL END)
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
            GROUP BY c.id, c.name
        """)
    List<PlayerCharacterDetails> findPlayerCharacterDetailsById(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.player.PlayerScriptDetails(
                s.id,
                s.name,
                COUNT(s.id),
                COUNT(DISTINCT CASE WHEN g.goodWon =
                    COALESCE(
                        (SELECT t.good FROM Transformation t
                            JOIN a.transformations at
                            WHERE t = at
                                AND t.id = (SELECT MAX(t2.id) FROM Transformation t2 JOIN a.transformations at2 WHERE t2 = at2)
                                AND t.type = 'BECOME'
                        ),
                        a.good
                    )
                    THEN g.id ELSE NULL END)
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
            GROUP BY s.id, s.name
        """)
    List<PlayerScriptDetails> findPlayerScriptDetailsById(long id);
}
