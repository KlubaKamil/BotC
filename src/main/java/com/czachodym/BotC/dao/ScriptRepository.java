package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.model.Script;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScriptRepository extends NameJpaRepository<Script, Long> {
    Optional<Script> findByName(String name);
}
