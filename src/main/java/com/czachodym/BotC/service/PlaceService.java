package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.PlaceRepository;
import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.model.Place;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.throwIfExistsByName;
import static com.czachodym.BotC.service.util.CommonMethods.throwIfNotFoundById;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final DtoMapper dtoMapper;

    public PlaceDto getPlace(long id){
        log.info("Checking if place exists.");
        Place place = throwIfNotFoundById(id, placeRepository);
        log.info("Place found.");
        return dtoMapper.mapPlace(place);
    }

    public List<PlaceDto> getAllPlaces(){
        log.info("Getting all places");
        List<Place> places = placeRepository.findAll();
        log.info("Places found.");
        return dtoMapper.mapPlaceList(places);
    }

    public long createPlace(PlaceDto placeDto){
        String name = placeDto.name();
        log.info("Checking if place exists.");
        throwIfExistsByName(name, placeRepository);
        Place place = buildPlace(placeDto);
        log.info("Saving new place.");
        Place savedPlace = placeRepository.save(place);
        long id = savedPlace.getId();
        log.info("Place saved: {}.", id);

        return id;
    }

    public long editPlace(PlaceDto placeDto){
        long id = placeDto.id();
        String name = placeDto.name();
        log.info("Checking if place exists.");
        Place place = throwIfNotFoundById(id, placeRepository);
        if(!place.getName().equals(placeDto.name())) {
            throwIfExistsByName(name, placeRepository);
        }
        log.info("Place found, updating.");
        Place savedPlace = buildPlace(placeDto, place);
        placeRepository.save(savedPlace);
        log.info("Place updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deletePlace(long id){
        log.info("Deleting a place.");
        boolean exists = placeRepository.existsById(id);
        placeRepository.deleteById(id);
        boolean deleted = exists & !placeRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    private Place buildPlace(PlaceDto placeDto){
        return buildPlace(placeDto, Place.builder());
    }

    private Place buildPlace(PlaceDto placeDto, Place place){
        Place.PlaceBuilder<?,?> placeBuilder = place.toBuilder()
                .id(place.getId());
        return buildPlace(placeDto, placeBuilder);
    }

    private Place buildPlace(PlaceDto placeDto, Place.PlaceBuilder<?,?> builder){
        return builder
                .name(placeDto.name())
                .build();
    }
}
