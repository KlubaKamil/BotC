package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.UserDto;
import com.czachodym.BotC.model.util.Role;
import com.czachodym.BotC.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return null;
    }

    @PostMapping("/member/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> memberUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to member user: {}, groupId: {}", userId, groupId);
        long id = userService.memberUser(groupId, userId);
        log.info("User set as member successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping("/unmember/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> unmemberUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to unmember user: {}, groupId: {}", userId, groupId);
        long id = userService.unmemberUser(groupId, userId);
        log.info("User deleted from being a member successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping("/mod/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> modUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to mod user: {}, groupId: {}", userId, groupId);
        long id = userService.modUser(groupId, userId);
        log.info("User set as moderator successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping("/unmod/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> unmodUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to unmod user: {}, groupId: {}", userId, groupId);
        long id = userService.unmodUser(groupId, userId);
        log.info("User deleted from being a moderator successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping("/admin/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> adminUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to admin user: {}, groupId: {}", userId, groupId);
        long id = userService.adminUser(groupId, userId);
        log.info("User set as administrator successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PostMapping("/unadmin/{groupId}/{userId}")
    public ResponseEntity<Map<String, Long>> unadminUser(@PathVariable long groupId, @PathVariable long userId){
        log.info("Trying to unmod user: {}, groupId: {}", userId, groupId);
        long id = userService.unadminUser(groupId, userId);
        log.info("User deleted from being an administrator successfully.");
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<UserDto>> getAllGroupUsers(@PathVariable long groupId){
        log.info("Getting all users for groupId: {}.", groupId);
        List<UserDto> groupUsers = userService.getGroupUsersByRole(groupId, null);
        log.info("Finished getting all users");
        return ResponseEntity.ok(groupUsers);
    }

    @GetMapping("/notgroup/{groupId}")
    public ResponseEntity<List<UserDto>> getAllNotGroupUsers(@PathVariable long groupId){
        log.info("Getting all users not in groupId: {}.", groupId);
        List<UserDto> users = userService.getAllUsersNotInGroup(groupId);
        log.info("Finished getting all users");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/group/{groupId}/{role}")
    public ResponseEntity<List<UserDto>> getGroupUsersByRole(@PathVariable long groupId, @PathVariable Role role){
        log.info("Getting users for groupId: {}, role: {}.", groupId, role);
        List<UserDto> groupUsers = userService.getGroupUsersByRole(groupId, role);
        log.info("Finished getting all users");
        return ResponseEntity.ok(groupUsers);
    }
}
