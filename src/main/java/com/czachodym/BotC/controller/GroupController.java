package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.GroupDto;
import com.czachodym.BotC.dto.UserDto;
import com.czachodym.BotC.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
