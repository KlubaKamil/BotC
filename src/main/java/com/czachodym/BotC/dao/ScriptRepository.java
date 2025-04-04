package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.dto.details.script.ScriptCharacterDetails;
import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.headers.ScriptHeader;
import com.czachodym.BotC.model.Script;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptRepository extends NameJpaRepository<Script, Long> {
    Optional<Script> findByName(String name);

    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.ScriptHeader(
                s.id,
                s.name,
                COUNT(g.id))
            FROM Script s
            LEFT JOIN Game g on s = g.script
            GROUP BY s.id, s.name
        """)
    List<ScriptHeader> findAllScriptHeaders();

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.script.ScriptDetails(
                COUNT(g.id),
                (SELECT COUNT(g2.id) FROM Game g2)
            )
            FROM Script s
            LEFT JOIN Game g ON g.script.id = s.id
            WHERE s.id = :id
            GROUP BY s.id
        """)
    Optional<ScriptDetails> findScriptDetailsById(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.script.ScriptCharacterDetails(
                c.id,
                c.name,
                COUNT(DISTINCT g.id),
                (SELECT COUNT(DISTINCT g2.id) FROM Game g2 where g2.script.id = :id),
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            )
            FROM Character c
            JOIN Assignment a ON c.id = a.character.id
            JOIN Game g ON g.id = (SELECT g2.id FROM Game g2 JOIN g2.assignments a2 WHERE a2.id = a.id)
            WHERE g.script.id = :id
            GROUP BY c.id, c.name
        """)
    List<ScriptCharacterDetails> findScriptCharacterDetailsByScriptId(long id);
}
