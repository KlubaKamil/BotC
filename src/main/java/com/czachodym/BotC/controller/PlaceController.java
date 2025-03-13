package com.czachodym.BotC.controller;


import com.czachodym.BotC.dto.PlaceDto;
import com.czachodym.BotC.service.PlaceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/place")
@Slf4j
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlace(@PathVariable long id){
        log.info("Getting a Place: {}", id);
        PlaceDto PlaceDto = placeService.getPlace(id);
        log.info("Finished getting a Place");
        return ResponseEntity.ok(PlaceDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaceDto>> getAllPlaces(){
        log.info("Getting all Places.");
        List<PlaceDto> PlaceDtos = placeService.getAllPlaces();
        log.info("Finished getting all Places");
        return ResponseEntity.ok(PlaceDtos);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> addPlace(@Valid @RequestBody PlaceDto PlaceDto){
        log.info("Creating new Place: {}", PlaceDto);
        long id = placeService.createPlace(PlaceDto);
        log.info("Finished creating new Place.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editPlace(@Valid @RequestBody PlaceDto PlaceDto){
        log.info("Editing an existing Place: {}", PlaceDto);
        long id = placeService.editPlace(PlaceDto);
        log.info("Finished editing an existing Place.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePlace(@PathVariable("id") long id){
        log.info("Deleting a Place: {}", id);
        placeService.deletePlace(id);
        log.info("Finished deleting a Place");
    }
}
