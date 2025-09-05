package com.czachodym.BotC.dao.util;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BotCNameJpaRepository<T, ID> extends BotCJpaRepository<T, ID> {
    List<T> findByNameIn(List<String> names);
    Optional<T> findByName(String name);
    boolean existsByName(String name);
}
