package com.czachodym.BotC.model.util;

import com.czachodym.BotC.model.Character;
import com.czachodym.BotC.model.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCharacterPair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    private boolean good;
}
