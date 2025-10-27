package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.BotCNameEntity;
import com.czachodym.BotC.model.util.PlayerAchievement;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class Player extends BotCNameEntity implements Serializable {
    @Column
    private String discordName;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_id")
    private List<PlayerAchievement> playerAchievements;
}
