package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.character.CharacterInScriptDetails;
import com.czachodym.BotC.dto.headers.CharacterHeader;
import com.czachodym.BotC.model.Character;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends NameJpaRepository<Character, Long> {
    boolean existsByName(String name);

    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.CharacterHeader(
                c.id,
                c.name,
                c.maxStartNumber,
                c.alignment,
                c.description,
                c.linkToWiki)
            FROM Character c
        """)
    List<CharacterHeader> findAllCharacterHeaders();

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.character.CharacterDetails(
                COUNT(DISTINCT g.id),
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            )
            FROM Game g
            JOIN g.assignments a
            WHERE a.character.id = :id
        """)
    Optional<CharacterDetails> findCharacterDetailsById(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.character.CharacterInScriptDetails(
                s.id,
                s.name,
                COUNT(DISTINCT CASE WHEN a.id IS NOT NULL THEN g.id ELSE NULL END),
                COUNT(DISTINCT CASE WHEN g.goodWon = a.good THEN g.id ELSE NULL END)
            )
            FROM Script s
            LEFT JOIN s.characters c
            LEFT JOIN Game g ON g.script.id = s.id
            LEFT JOIN g.assignments a ON a.character.id = :id
            WHERE c.id = :id
            GROUP BY s.id, s.name
            ORDER BY COUNT(DISTINCT g.id) DESC
        """)
    List<CharacterInScriptDetails> findCharacterInScriptDetailsById(long id);
}
