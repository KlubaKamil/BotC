package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.PlaceRepository;
import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.model.Place;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final DtoMapper dtoMapper;
    private final Validators validators;

    public PlaceDto getPlace(long id, long groupId){
        log.info("Checking if place exists.");
        Place place = validators.throwIfNotFoundByIdAndGroupId(id, groupId, placeRepository);
        log.info("Place found.");
        return dtoMapper.mapPlace(place);
    }

    public List<PlaceDto> getAllPlaces(long groupId){
        log.info("Getting all places");
        List<Place> places = placeRepository.findByGroups_Id(groupId);
        log.info("Places found.");
        return dtoMapper.mapPlaceList(places);
    }

    public long createPlace(long groupId, PlaceDto placeDto){
        String name = placeDto.name();
        log.info("Checking if place exists.");
        validators.throwIfExistsByName(name, placeRepository);
        Place place = buildPlace(groupId, placeDto);
        log.info("Saving new place.");
        Place savedPlace = placeRepository.save(place);
        long id = savedPlace.getId();
        log.info("Place saved: {}.", id);

        return id;
    }

    public long editPlace(long groupId, PlaceDto placeDto){
        long id = placeDto.id();
        String name = placeDto.name();
        log.info("Checking if place exists.");
        Place place = validators.throwIfNotFoundByIdAndGroupId(id, groupId, placeRepository);
        if(!place.getName().equals(placeDto.name())) {
            validators.throwIfExistsByName(name, placeRepository);
        }
        log.info("Place found, updating.");
        Place savedPlace = buildPlace(groupId, placeDto, place);
        placeRepository.save(savedPlace);
        log.info("Place updated. Id: {}", id);

        return id;
    }

    @Transactional
    public void deletePlace(long groupId, long id){
        log.info("Deleting a place.");
        boolean exists = placeRepository.existsById(id);
        placeRepository.deleteById(id);
        boolean deleted = exists & !placeRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    private Place buildPlace(long groupId, PlaceDto placeDto){
        return buildPlace(groupId, placeDto, new Place());
    }

    private Place buildPlace(long groupId, PlaceDto placeDto, Place place){
        log.info("Validating a placeDto.");

        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<Group> groups = place.getGroups();
        groups.add(group);
        log.info("Group available.");

        log.info("Validation successful, building place.");
        return place.toBuilder()
                .groups(groups)
                .name(placeDto.name())
                .build();
    }
}
