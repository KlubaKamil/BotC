package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.model.Character;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends NameJpaRepository<Character, Long> {
    boolean existsByName(String name);
}
