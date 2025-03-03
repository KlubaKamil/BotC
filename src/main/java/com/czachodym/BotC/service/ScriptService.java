package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dao.ScriptRepository;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Script;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScriptService {
    private final CharacterRepository characterRepository;
    private final ScriptRepository scriptRepository;
    private final DtoMapper dtoMapper;

    public ScriptDto getScript(long id){
        log.info("Checking if script exists.");
        Script script = throwIfNotFoundById(id, scriptRepository);
        log.info("Script found.");
        return dtoMapper.mapScript(script);
    }

    public List<ScriptDto> getAllScripts(){
        log.info("Getting all scripts");
        List<Script> scripts = scriptRepository.findAll();
        log.info("Scripts found.");
        return dtoMapper.mapScriptList(scripts);
    }

    public long createScript(ScriptDto scriptDto){
        String name = scriptDto.name();
        log.info("Checking if script exists.");
        throwIfExistsByName(name, scriptRepository);
        Script script = buildScript(scriptDto);
        log.info("Saving a new script.");
        Script savedScript = scriptRepository.save(script);
        long id = savedScript.getId();
        log.info("Script saved. Id: {}", id);

        return id;
    }

    public long editScript(ScriptDto scriptDto){
        long id = scriptDto.id();
        String name = scriptDto.name();
        log.info("Checking if script exists.");
        Script script = throwIfNotFoundById(id, scriptRepository);
        if(!script.getName().equals(scriptDto.name())) {
            throwIfExistsByName(name, scriptRepository);
        }
        log.info("Script found, updating.");
        Script updatedScript = buildScript(scriptDto, script);
        scriptRepository.save(updatedScript);
        log.info("Script updated. Id: {}", updatedScript);

        return id;
    }

    @Transactional
    public void deleteScript(long id){
        log.info("Deleting a script: {}", id);
        boolean exists = scriptRepository.existsById(id);
        scriptRepository.deleteById(id);
        boolean deleted = exists & !scriptRepository.existsById(id);
        log.info("Deleted: {}", deleted);
    }

    private Script buildScript(ScriptDto scriptDto){
        return buildScript(scriptDto, Script.builder());
    }

    private Script buildScript(ScriptDto scriptDto, Script script){
        Script.ScriptBuilder<?,?> scriptBuilder = script.toBuilder()
                .id(script.getId());
        return buildScript(scriptDto, scriptBuilder);
    }

    private Script buildScript(ScriptDto scriptDto, Script.ScriptBuilder<?,?> builder){
        log.info("Validating a scriptDto");
        List<Long> characterIds = scriptDto.characters().stream()
                .map(CharacterDto::id)
                .toList();
        log.info("Script not found, looking for scriptAssignments: {}", characterIds);
        List<Character> characters = findEntities(characterIds, characterRepository);
        log.info("Characters found, validation successful. Building a script.");

        return builder
                .name(scriptDto.name())
                .characters(characters)
                .build();
    }
}
