package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.NameJpaRepository;
import com.czachodym.BotC.model.Place;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends NameJpaRepository<Place, Long> {
}
