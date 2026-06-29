package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.DiscordGuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscordGuildRepository extends JpaRepository<DiscordGuild, Long> {
    Optional<DiscordGuild> findByDiscordGuildId(String discordGuildId);
    List<DiscordGuild> findAllByGroups_Id(long groupId);
}
