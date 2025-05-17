package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dao.ScriptRepository;
import com.czachodym.BotC.dto.CharacterDto;
import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.dto.details.script.ScriptCharacterDetails;
import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.headers.ScriptHeader;
import com.czachodym.BotC.model.Alignment;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Script;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.czachodym.BotC.service.util.CommonMethods.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScriptService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final CharacterRepository characterRepository;
    private final ScriptRepository scriptRepository;
    private final DtoMapper dtoMapper;

    public ScriptDto getScript(long id){
        log.info("Checking if script exists.");
        Script script = throwIfNotFoundById(id, scriptRepository);
        log.info("Script found, getting details.");
        ScriptDetails scriptDetails = scriptRepository.findScriptDetailsById(id).orElseThrow();
        List<ScriptCharacterDetails> scriptCharacterDetails = scriptRepository.findScriptCharacterDetailsByScriptId(id);
        scriptDetails = scriptDetails.toBuilder()
                .scriptCharactersDetails(scriptCharacterDetails)
                .build();
        log.info("Details found.");
        return dtoMapper.mapScript(script, scriptDetails);
    }

    public List<ScriptDto> getAllScripts(){
        log.info("Getting all scripts");
        List<Script> scripts = scriptRepository.findAll();
        log.info("Scripts found.");
        return dtoMapper.mapScriptList(scripts);
    }

    public List<ScriptHeader> getAllScriptHeaders(){
        log.info("Getting all script headers");
        List<ScriptHeader> scriptHeaders = scriptRepository.findAllScriptHeaders();
        log.info("Headers found.");
        return scriptHeaders;
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

    public String getMessage(long id, NotificationMode notificationMode){
        Script script = throwIfNotFoundById(id, scriptRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nowy skrypt!" : "Edytowano skrypt!";
        String name = script.getName();
        String author = getIfNull(script.getAuthor(), "-");
        String notes = getIfNull(script.getNotes(), "-");
        String townsfolks = getCharactersAsString(script.getCharacters(), Alignment.Townsfolk);
        String outsiders = getCharactersAsString(script.getCharacters(), Alignment.Outsider);
        String minions = getCharactersAsString(script.getCharacters(), Alignment.Minion);
        String demons = getCharactersAsString(script.getCharacters(), Alignment.Demon);
        return """
                %s
                Id: %d
                Nazwa: %s
                Autor: %s
                Wskazówki: %s
                Postacie:
                   Townsfolkowie: %s
                   Outsiderzy: %s
                   Miniony: %s
                   Demony: %s
                Kliknij i zobacz: %s/scripts/%d
                """.formatted(modeMessage, id, name, author, notes, townsfolks, outsiders, minions, demons,
                    FRONTEND_URL, id);
    }

    private String getCharactersAsString(List<Character> characters, Alignment alignment){
        List<String> filteredCharactersNames = characters.stream()
                .filter(c -> c.getAlignment() == alignment)
                .map(Character::getName)
                .toList();
        return String.join(", ", filteredCharactersNames);
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
        List<Character> characters = findEntitiesById(characterIds, characterRepository)
                .stream().sorted(Comparator.comparingLong(c -> characterIds.indexOf(c.getId())))
                .toList();
        log.info("Characters found, validation successful. Building a script.");

        return builder
                .name(scriptDto.name())
                .author(scriptDto.author())
                .notes(scriptDto.notes())
                .characters(characters)
                .build();
    }
}
