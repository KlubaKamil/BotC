package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.PlayerCharacterPair;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;
    @ManyToOne
    @JoinColumn(name = "storyteller_id")
    private Player storyteller;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_id")
    private List<PlayerCharacterPair> assignments;
    @Column
    private boolean goodWon;
    @Column
    private LocalDateTime date;
}
