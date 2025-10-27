package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.User;
import com.czachodym.BotC.model.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByDiscordId(String discordId);
    List<User> findByGroupRoles_Group_Id(long groupId);
    List<User> findByGroupRoles_Group_IdAndGroupRoles_Role(long groupId, Role role);


    @Query("""
            SELECT u
            FROM User u
            WHERE NOT EXISTS (
                SELECT gr
                FROM u.groupRoles gr
                WHERE gr.group.id = :groupId
            )
            AND NOT EXISTS (
                SELECT gr2
                FROM u.groupRoles gr2
                WHERE gr2.role = 'GLOBAL_ADMIN'
            )
        """)
    List<User> findAllWithoutGroupIdExcludingGlobalAdmin(@Param("groupId") long groupId);
}
