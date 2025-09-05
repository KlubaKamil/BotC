package com.czachodym.BotC.service.util;

import com.czachodym.BotC.dao.util.BotCJpaRepository;
import com.czachodym.BotC.dao.util.BotCNameJpaRepository;
import com.czachodym.BotC.exception.EntityAlreadyExistsException;
import com.czachodym.BotC.exception.EntityNotFoundException;
import com.czachodym.BotC.exception.GroupNotAllowedException;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.model.util.CurrentUser;
import com.czachodym.BotC.model.util.BotCEntity;
import com.czachodym.BotC.model.util.BotCNameEntity;
import com.czachodym.BotC.model.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Validators {
    private final CurrentUser currentUser;

    public Group throwIfGroupNotAvailableMember(long groupId){
        return currentUser.getGroupRoles().stream()
                .filter(gr -> gr.getGroup().getId() == groupId &&
                        List.of(Role.MEMBER, Role.MODERATOR, Role.GROUP_ADMIN).contains(gr.getRole()))
                .findFirst()
                .orElseThrow(() -> new GroupNotAllowedException(groupId))
                .getGroup();
    }

    public Group throwIfGroupNotAvailableMod(long groupId){
        return currentUser.getGroupRoles().stream()
                .filter(gr -> gr.getGroup().getId() == groupId &&
                        List.of(Role.MODERATOR, Role.GROUP_ADMIN).contains(gr.getRole()))
                .findFirst()
                .orElseThrow(() -> new GroupNotAllowedException(groupId))
                .getGroup();
    }

    public Group throwIfGroupNotAvailableAdmin(long groupId){
        return currentUser.getGroupRoles().stream()
                .filter(gr -> gr.getGroup().getId() == groupId && Role.GROUP_ADMIN == gr.getRole())
                .findFirst()
                .orElseThrow(() -> new GroupNotAllowedException(groupId))
                .getGroup();
    }

    //TODO
    public <T extends BotCEntity> List<T> throwIfEntitiesNotExist(List<Long> ids, long groupId, BotCNameJpaRepository<T, Long> repository){
        List<T> entities = repository.findByIdInAndGroups_Id(ids, groupId);
        List<Long> actualIds = entities.stream()
                .map(T::getId)
                .toList();
        if(!new HashSet<>(actualIds).containsAll(ids)) {
            throw new EntityNotFoundException(ids, actualIds);
        }
        return entities;
    }

    //TODO
    public <T> void throwIfExistsByName(String name, BotCNameJpaRepository<T, Long> repository){
        boolean exists = repository.existsByName(name);
        if(exists){
            throw new EntityAlreadyExistsException(name);
        }
    }

    public <T> T throwIfNotFoundByIdAndGroupId(long id, long groupId, BotCJpaRepository<T, Long> repository){
        return repository
                .findByIdAndGroups_Id(id, groupId)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public String getIfNotNull(String value, String otherwise){
        if(value == null || value.isBlank() || value.isEmpty()){
            return otherwise;
        }
        return value;
    }

    public String getIfBotCEntityNotNull(BotCNameEntity value, String otherwise){
        if(value == null || value.getName() == null || value.getName().isBlank() || value.getName().isEmpty()){
            return otherwise;
        }
        return value.getName();
    }
}
