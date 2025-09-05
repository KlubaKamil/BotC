package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.BotCNameJpaRepository;
import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.dto.details.character.CharacterInScriptDetails;
import com.czachodym.BotC.dto.headers.CharacterHeader;
import com.czachodym.BotC.model.Character;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends BotCNameJpaRepository<Character, Long> {
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
            JOIN c.groups gr
            WHERE gr.id = :groupId
        """)
    List<CharacterHeader> findAllCharacterHeaders(long groupId);

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
            LEFT JOIN s.scriptCharacters sc
            LEFT JOIN sc.character c
            LEFT JOIN Game g ON g.script.id = s.id
            LEFT JOIN g.assignments a
            WHERE c.id = :id
            GROUP BY s.id, s.name
            ORDER BY COUNT(DISTINCT g.id) DESC
        """)
    List<CharacterInScriptDetails> findCharacterInScriptDetailsById(long id);
}
