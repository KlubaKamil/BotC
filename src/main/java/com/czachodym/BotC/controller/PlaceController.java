package com.czachodym.BotC.controller;


import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/place/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlace(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a place: id: {}, groupId: {}", id, groupId);
        PlaceDto PlaceDto = placeService.getPlace(id, groupId);
        log.info("Finished getting a place");
        return ResponseEntity.ok(PlaceDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaceDto>> getAllPlaces(@PathVariable long groupId){
        log.info("Getting all places for groupId: {}.", groupId);
        List<PlaceDto> PlaceDtos = placeService.getAllPlaces(groupId);
        log.info("Finished getting all places");
        return ResponseEntity.ok(PlaceDtos);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> addPlace(@PathVariable long groupId, @Valid @RequestBody PlaceDto placeDto){
        log.info("Creating new place, groupId: {}, dto: {}", groupId, placeDto);
        long id = placeService.createPlace(groupId, placeDto);
        log.info("Finished creating new place.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editPlace(@PathVariable long groupId, @Valid @RequestBody PlaceDto placeDto){
        log.info("Editing an existing place, groupId: {}, dto: {}", groupId, placeDto);
        long id = placeService.editPlace(groupId, placeDto);
        log.info("Finished editing an existing place.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePlace(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a place, id: {}, groupId: {}", id, groupId);
        placeService.deletePlace(groupId, id);
        log.info("Finished deleting a place");
    }
}
