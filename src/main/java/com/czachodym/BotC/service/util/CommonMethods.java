package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.exception.EntityAlreadyExistsException;
import com.czachodym.BotC.exception.EntityNotFoundException;
import com.czachodym.BotC.model.util.BotCEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Slf4j
public class CommonMethods {
    public static <T extends BotCEntity> List<T> findEntities(List<Long> ids, NameJpaRepository<T, Long> repository){
        List<T> entities = repository.findAllById(ids);
        if(entities.size() != ids.size()) {
            List<Long> actualIds = entities.stream()
                    .map(T::getId)
                    .toList();
            throw new EntityNotFoundException(ids, actualIds);
        }
        return entities;
    }

    public static <T> void throwIfExistsByName(String name, NameJpaRepository<T, Long> repository){
        boolean exists = repository.existsByName(name);
        if(exists){
            throw new EntityAlreadyExistsException(name);
        }
    }

    public static <T> T throwIfNotFoundById(long id, JpaRepository<T, Long> repository){
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
