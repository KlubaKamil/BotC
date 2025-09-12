package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.CharacterRepository;
import com.czachodym.BotC.dao.ScriptRepository;
import com.czachodym.BotC.dto.ScriptDto;
import com.czachodym.BotC.dto.details.script.ScriptCharacterDetails;
import com.czachodym.BotC.dto.details.script.ScriptDetails;
import com.czachodym.BotC.dto.headers.ScriptHeader;
import com.czachodym.BotC.model.Alignment;
import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.model.Script;
import com.czachodym.BotC.model.util.ScriptCharacter;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import com.czachodym.botcshared.dto.NotificationMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScriptService {
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final CharacterRepository characterRepository;
    private final ScriptRepository scriptRepository;
    private final DtoMapper dtoMapper;
    private final Validators validators;

    public ScriptDto getScript(long id, long groupId){
        log.info("Checking if script exists.");
        Script script = validators.throwIfNotFoundByIdAndGroupId(id, groupId, scriptRepository);
        log.info("Script found, getting details.");
        ScriptDetails scriptDetails = scriptRepository.findScriptDetailsById(id).orElseThrow();
        List<ScriptCharacterDetails> scriptCharacterDetails = scriptRepository.findScriptCharacterDetailsByScriptId(id);
        scriptDetails = scriptDetails.toBuilder()
                .scriptCharactersDetails(scriptCharacterDetails)
                .build();
        log.info("Details found.");
        return dtoMapper.mapScript(script, scriptDetails);
    }

    public List<ScriptDto> getAllScripts(long groupId){
        log.info("Getting all scripts");
        List<Script> scripts = scriptRepository.findByGroups_Id(groupId);
        log.info("Scripts found.");
        return dtoMapper.mapScriptList(scripts);
    }

    public List<ScriptHeader> getAllScriptHeaders(long groupId){
        log.info("Getting all script headers");
        List<ScriptHeader> scriptHeaders = scriptRepository.findAllScriptHeaders(groupId);
        log.info("Headers found.");
        return scriptHeaders;
    }

    public long createScript(long groupId, ScriptDto scriptDto){
        String name = scriptDto.name();
        log.info("Checking if script exists.");
        validators.throwIfExistsByNameAndGroupId(groupId, name, scriptRepository);
        Script script = buildScript(groupId, scriptDto);
        log.info("Saving a new script.");
        Script savedScript = scriptRepository.save(script);
        long id = savedScript.getId();
        log.info("Script saved. Id: {}", id);

        return id;
    }

    public long editScript(long groupId, ScriptDto scriptDto){
        long id = scriptDto.id();
        String name = scriptDto.name();
        log.info("Checking if script exists.");
        Script script = validators.throwIfNotFoundByIdAndGroupId(id, groupId, scriptRepository);
        if(!script.getName().equals(scriptDto.name())) {
            validators.throwIfExistsByNameAndGroupId(groupId, name, scriptRepository);
        }
        log.info("Script found, updating.");
        Script updatedScript = buildScript(groupId, scriptDto, script);
        scriptRepository.save(updatedScript);
        log.info("Script updated. Id: {}", updatedScript);

        return id;
    }

    @Transactional
    public void deleteScript(long id, long groupId){
        log.info("Deleting a script: {}", id);

        log.info("Checking if group available: {}.", groupId);
        validators.throwIfGroupNotAvailableMod(groupId);
        log.info("Checking if script exists: {}.", id);
        validators.throwIfNotFoundByIdAndGroupId(id, groupId, scriptRepository);
        scriptRepository.deleteById(id);
        boolean deleted = scriptRepository.existsById(id);

        log.info("Deleted: {}", deleted);
    }

    public String getMessage(long id, NotificationMode notificationMode){
        Script script = validators.throwIfNotFoundByIdAndGroupId(id, 0, scriptRepository);
        String modeMessage = notificationMode == NotificationMode.NEW ? "Dodano nowy skrypt!" : "Edytowano skrypt!";
        String name = script.getName();
        String author = validators.getIfNotNull(script.getAuthor(), "-");
        String notes = validators.getIfNotNull(script.getNotes(), "-");
        String townsfolks = getCharactersAsString(script.getScriptCharacters(), Alignment.Townsfolk);
        String outsiders = getCharactersAsString(script.getScriptCharacters(), Alignment.Outsider);
        String minions = getCharactersAsString(script.getScriptCharacters(), Alignment.Minion);
        String demons = getCharactersAsString(script.getScriptCharacters(), Alignment.Demon);
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

    private String getCharactersAsString(List<ScriptCharacter> scriptCharacters, Alignment alignment){
        List<String> filteredCharactersNames = scriptCharacters.stream()
                .filter(c -> c.getCharacter().getAlignment() == alignment)
                .map(c -> c.getCharacter().getName())
                .toList();
        return String.join(", ", filteredCharactersNames);
    }

    private Script buildScript(long groupId, ScriptDto scriptDto){
        return buildScript(groupId, scriptDto, new Script());
    }

    private Script buildScript(long groupId, ScriptDto scriptDto, Script script){
        log.info("Validating a scriptDto");

        log.info("Checking if groupId available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<Group> groups = script.getGroups();
        groups.add(group);

        log.info("Group available,. looking for characters");
        List<Long> characterIds = scriptDto.scriptCharacters().stream()
                .map(sc -> sc.character().id())
                .toList();
        List<Character> characters = validators.throwIfEntitiesNotExistByGroupId(characterIds, groupId, characterRepository)
                .stream().sorted(Comparator.comparingLong(c -> characterIds.indexOf(c.getId())))
                .toList();
        List<ScriptCharacter> scriptCharacters = new ArrayList<>(characters.size());
        for(int i = 0; i < characters.size(); i++){
            scriptCharacters.add(ScriptCharacter.builder()
                    .character(characters.get(i))
                    .characterOrder(i)
                    .build());
        }
        log.info("Characters found.");

        log.info("Validation successful. Building a script.");
        return script.toBuilder()
                .groups(groups)
                .name(scriptDto.name())
                .author(scriptDto.author())
                .notes(scriptDto.notes())
                .scriptCharacters(scriptCharacters)
                .build();
    }
}
