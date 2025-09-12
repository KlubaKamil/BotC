package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GroupDto;
import com.czachodym.BotC.dto.UserDto;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/all")
    public ResponseEntity<List<GroupDto>> getAllGroups(){
        log.info("Trying to get all groups.");
        List<GroupDto> groupDtos = groupService.getAllGroups();
        log.info("Finished getting all groups.");
        return ResponseEntity.ok(groupDtos);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Map<String, Long>> createGroup(@PathVariable String name){
        log.info("Creating a new group: {}", name);
        Group group = groupService.createGroup(name);
        log.info("Group created: {}", group.getId());
        return ResponseEntity.ok(Map.of("id", group.getId()));
    }
}
