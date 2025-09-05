package com.czachodym.BotC.service;

import com.czachodym.BotC.dao.UserRepository;
import com.czachodym.BotC.dto.UserDto;
import com.czachodym.BotC.model.Group;
import com.czachodym.BotC.model.util.GroupRole;
import com.czachodym.BotC.model.User;
import com.czachodym.BotC.model.util.Role;
import com.czachodym.BotC.service.util.DtoMapper;
import com.czachodym.BotC.service.util.Validators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Validators validators;
    private final DtoMapper dtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> loadUserByDiscordId(String id){
        return userRepository.findByDiscordId(id);
    }

    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<UserDto> getGroupUsersByRole(long groupId, Role role){
        log.info("Getting all group users: {} by role: {}", groupId, role);
        List<User> users;
        if(role == null) {
            users = userRepository.findByGroupRoles_Group_Id(groupId);
        } else {
            users = userRepository.findByGroupRoles_Group_IdAndGroupRoles_Role(groupId, role);
        }
        log.info("Users found.");
        return dtoMapper.mapUserList(users);
    }

    public long createUser(){
        return -1;
    }

    public User createUserWithDiscord(String discordId, String globalName){
        log.info("Trying to create used from discord.");
        Optional<User> optionalUser = getUser(globalName);
        String usernameToSave = globalName;
        if(optionalUser.isPresent()){
            log.info("User with given name already exists, adding \"1\" to new name.");
            usernameToSave += "1";
        }
        User user = User.builder()
                .discordId(discordId)
                .username(usernameToSave)
                .build();
        User savedUser = userRepository.save(user);
        long id = savedUser.getId();
        log.info("User saved. Id: {}, discordId: {}", id, discordId);

        return user;
    }

    public long memberUser(long groupId, String username){
        log.info("Checking if user exists: {}", username);
        User user = (User) loadUserByUsername(username);
        log.info("User exists, checking if group is available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<GroupRole> groupRoles = user.getGroupRoles();
        validateUserGroupRole(groupRoles, group);
        log.info("Validation successful, setting user to mod.");
        groupRoles.removeIf(g -> g.getGroup().equals(group));
        groupRoles.add(GroupRole.builder()
                .group(group)
                .role(Role.MEMBER)
                .build());
        userRepository.save(user);
        log.info("User set to member of groupId: {}", groupId);

        return user.getId();
    }

    public long unmemberUser(long groupId, String username) {
        log.info("Checking if user exists: {}", username);
        User user = (User) loadUserByUsername(username);

        log.info("User exists, checking if group is available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableMod(groupId);
        Set<GroupRole> groupRoles = user.getGroupRoles();
        GroupRole currentGroupRole = groupRoles.stream()
                .filter(g -> g.getGroup().equals(group))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User it not a member of a group."));
        if(currentGroupRole.getRole() == Role.MODERATOR){
            throw new IllegalArgumentException("User is a moderator of the group.");
        } else if(currentGroupRole.getRole() == Role.GROUP_ADMIN){
            throw new IllegalArgumentException("User is an admin of the group.");
        }
        log.info("EBEGROUP: {}", group);
        log.info("EBE: {}", groupRoles);
        groupRoles.removeIf(g -> g.getGroup().equals(group));
        log.info("EBE2: {}", groupRoles);
        userRepository.save(user);
        log.info("User deleted form being a member of groupId: {}", groupId);

        return user.getId();
    }


    public long modUser(long groupId, String username) {
        log.info("Checking if user exists: {}", username);
        User user = (User) loadUserByUsername(username);
        log.info("User exists, checking if group is available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableAdmin(groupId);
        Set<GroupRole> groupRoles = user.getGroupRoles();
        validateUserGroupRole(groupRoles, group);
        log.info("Validation successful, setting user to mod.");
        groupRoles.removeIf(g -> g.getGroup().equals(group));
        groupRoles.add(GroupRole.builder()
                .group(group)
                .role(Role.MODERATOR)
                .build());
        userRepository.save(user);
        log.info("User set to moderator of groupId: {}", groupId);

        return user.getId();
    }

    public long unmodUser(long groupId, String username) {
        log.info("Checking if user exists: {}", username);
        User user = (User) loadUserByUsername(username);

        log.info("User exists, checking if group is available: {}", groupId);
        Group group = validators.throwIfGroupNotAvailableAdmin(groupId);
        Set<GroupRole> groupRoles = user.getGroupRoles();
        GroupRole currentGroupRole = groupRoles.stream()
                .filter(g -> g.getGroup().equals(group))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User it not a member of a group."));
        if(currentGroupRole.getRole() == Role.MEMBER){
            throw new IllegalArgumentException("User is only a member of the group.");
        } else if(currentGroupRole.getRole() == Role.GROUP_ADMIN){
            throw new IllegalArgumentException("User is an admin of the group.");
        }
        groupRoles.removeIf(g -> g.getGroup().equals(group));
        groupRoles.add(GroupRole.builder()
                .group(group)
                .role(Role.MEMBER)
                .build());
        userRepository.save(user);
        log.info("User deleted form being a moderator of groupId: {}", groupId);

        return user.getId();
    }

    private void validateUserGroupRole(Set<GroupRole> groupRoles, Group group){
        GroupRole groupRoleAdmin = GroupRole.builder().group(group).role(Role.GROUP_ADMIN).build();
        if(groupRoles.contains(groupRoleAdmin)){
            throw new IllegalArgumentException("User already is a admin of the group.");
        }
        GroupRole groupRoleMod = GroupRole.builder().group(group).role(Role.MODERATOR).build();
        if(groupRoles.contains(groupRoleMod)){
            throw new IllegalArgumentException("User already is a moderator of the group.");
        }
    }
}
