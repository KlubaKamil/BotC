package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.Assignment;
import com.czachodym.BotC.model.util.BalanceMark;
import com.czachodym.BotC.model.util.BotCEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Game extends BotCEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;
    @ManyToAny
    @JoinColumn(name = "storyteller_id")
    private List<Player> storytellers;
    @ManyToAny
    @JoinColumn(name = "fabled_id")
    private List<Character> fables;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<Assignment> assignments;
    @Column
    private boolean goodWon;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Lob
    private String notes;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
    @Column
    private boolean imageUploaded;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BalanceMark> balanceMarks;
}
