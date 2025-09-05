package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.GroupRepository;
import com.czachodym.BotC.dto.GroupDto;
import com.czachodym.BotC.dto.UserDto;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.service.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final DtoMapper dtoMapper;

    public List<GroupDto> getAllGroups(){
        log.info("Getting all groups");
        List<Group> groups = groupRepository.findAll();
        log.info("Groups found.");
        return dtoMapper.mapGroup(groups);
    }
}
