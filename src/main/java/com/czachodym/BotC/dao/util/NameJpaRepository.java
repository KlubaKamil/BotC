package com.czachodym.BotC.dao.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface NameJpaRepository<T, ID> extends JpaRepository<T, ID> {
    List<T> findByNameIn(List<String> names);
    Optional<T> findByName(String name);
    boolean existsByName(String name);
    int deleteByName(String name);
}
