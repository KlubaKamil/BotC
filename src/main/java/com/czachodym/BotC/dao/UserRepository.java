package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.User;
import com.czachodym.BotC.model.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByDiscordId(String discordId);
    List<User> findByGroupRoles_Group_Id(long groupId);
    List<User> findByGroupRoles_Group_IdAndGroupRoles_Role(long groupId, Role role);
}
