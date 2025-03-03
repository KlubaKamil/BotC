package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.service.ScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/script")
@Slf4j
public class ScriptController {
    @Autowired
    private ScriptService scriptService;

    @GetMapping("/{id}")
    public ResponseEntity<ScriptDto> getScript(@PathVariable long id){
        log.info("Getting a script: {}", id);
        ScriptDto scriptDto = scriptService.getScript(id);
        log.info("Finished getting a script");
        return ResponseEntity.ok(scriptDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScriptDto>> getAllScripts(){
        log.info("Getting all scripts.");
        List<ScriptDto> scriptDtos = scriptService.getAllScripts();
        log.info("Finished getting all scripts");
        return ResponseEntity.ok(scriptDtos);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createScript(@RequestBody ScriptDto scriptDto){
        log.info("Creating new script: {}", scriptDto);
        long id = scriptService.createScript(scriptDto);
        log.info("Finished creating new script.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editScript(@RequestBody ScriptDto scriptDto){
        log.info("Editing an existing script: {}", scriptDto);
        long id = scriptService.editScript(scriptDto);
        log.info("Finished editing an existing game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteScript(@PathVariable("id") long id){
        log.info("Deleting a script: {}", id);
        scriptService.deleteScript(id);
        log.info("Finished deleting a script");
    }
}
