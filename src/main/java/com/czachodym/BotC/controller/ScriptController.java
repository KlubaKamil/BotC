package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.dto.headers.ScriptHeader;
import com.czachodym.BotC.service.ScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/script/{groupId}")
@RequiredArgsConstructor
@Slf4j
public class ScriptController {
    private final ScriptService scriptService;

    @GetMapping("/{id}")
    public ResponseEntity<ScriptDto> getScript(@PathVariable long id, @PathVariable long groupId){
        log.info("Getting a script, id: {}, groupId: {}", id, groupId);
        ScriptDto scriptDto = scriptService.getScript(id, groupId);
        log.info("Finished getting a script.");
        return ResponseEntity.ok(scriptDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScriptDto>> getAllScripts(@PathVariable long groupId){
        log.info("Getting all scripts for groupId: {}.", groupId);
        List<ScriptDto> scriptDtos = scriptService.getAllScripts(groupId);
        log.info("Finished getting all scripts.");
        return ResponseEntity.ok(scriptDtos);
    }

    @GetMapping("/headers")
    public ResponseEntity<List<ScriptHeader>> getAllGameHeaders(@PathVariable long groupId){
        log.info("Getting all script headers for groupId: {}.", groupId);
        List<ScriptHeader> scriptHeaders = scriptService.getAllScriptHeaders(groupId);
        log.info("Finished getting all script headers.");
        return ResponseEntity.ok(scriptHeaders);
    }

    @PutMapping
    public ResponseEntity<Map<String, Long>> createScript(@PathVariable long groupId, @RequestBody ScriptDto scriptDto){
        log.info("Creating new script, groupId: {}, dto: {}", groupId, scriptDto);
        long id = scriptService.createScript(groupId, scriptDto);
        log.info("Finished creating new script.");
        return ResponseEntity.status(CREATED).body(Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> editScript(@PathVariable long groupId, @RequestBody ScriptDto scriptDto){
        log.info("Editing an existing script, groupId: {}, id: {}", groupId, scriptDto);
        long id = scriptService.editScript(groupId ,scriptDto);
        log.info("Finished editing an existing game.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteScript(@PathVariable long id, @PathVariable long groupId){
        log.info("Deleting a script, id: {}, groupId: {}", id, groupId);
        scriptService.deleteScript(id, groupId);
        log.info("Finished deleting a script");
    }
}
