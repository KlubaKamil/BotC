package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.BotCNameJpaRepository;
import com.czachodym.BotC.model.Place;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends BotCNameJpaRepository<Place, Long> {
}
