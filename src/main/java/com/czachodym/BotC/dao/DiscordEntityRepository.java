package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.DiscordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscordEntityRepository extends JpaRepository<DiscordEntity, Long> {
    Optional<DiscordEntity> findByDiscordEntityId(String discordEntityId);
    List<DiscordEntity> findAllByGroups_Id(long groupId);
    List<DiscordEntity> findAllByGuildIdAndGroups_Id(String guildId, long groupId);
    void deleteAllByGroups_Id(long groupId);
}
