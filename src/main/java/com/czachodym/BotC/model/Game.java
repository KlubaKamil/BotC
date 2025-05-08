package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.Assignment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;
    @ManyToOne
    @JoinColumn(name = "storyteller_id")
    private Player storyteller;
    @ManyToOne
    @JoinColumn(name = "fabled_id")
    private Character fabled;
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
    Place place;
    @Column
    private String imageUrl;
    @ElementCollection
    @CollectionTable(name = "balance_marks", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "number")
    private List<Integer> balanceMarks;
}
