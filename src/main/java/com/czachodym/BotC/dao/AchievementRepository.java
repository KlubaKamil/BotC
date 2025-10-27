package com.czachodym.BotC.dao;

import com.czachodym.BotC.dao.util.BotCNameJpaRepository;
import com.czachodym.BotC.dto.details.achievement.AchievementDetails;
import com.czachodym.BotC.dto.details.achievement.AchievementPlayerDetails;
import com.czachodym.BotC.dto.headers.AchievementHeader;
import com.czachodym.BotC.model.Achievement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends BotCNameJpaRepository<Achievement, Long> {
    @Query("""
            SELECT new com.czachodym.BotC.dto.headers.AchievementHeader(
                a.id,
                a.name,
                a.description
            )
            FROM Achievement a
            JOIN a.groups gr
            WHERE gr.id = :groupId
        """)
    List<AchievementHeader> findAllAchievementHeaders(long groupId);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.achievement.AchievementDetails(
                count(pa.id)
            )
            FROM Achievement a
            LEFT JOIN PlayerAchievement pa on a = pa.achievement
            WHERE a.id = :id
        """)
    AchievementDetails findAchievementDetails(long id);

    @Query("""
            SELECT new com.czachodym.BotC.dto.details.achievement.AchievementPlayerDetails(
                p.id,
                p.name,
                pa.date
            )
            FROM Achievement a
            LEFT JOIN PlayerAchievement pa on a = pa.achievement
            LEFT JOIN Player p on p.id = (
                SELECT p2.id FROM Player p2
                JOIN p2.playerAchievements pa2
                WHERE pa2.id = pa.id
            )
            WHERE a.id = :id
        """)
    List<AchievementPlayerDetails> findAchievementPlayerDetails(long id);
}
