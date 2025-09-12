package com.czachodym.BotC.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "group_")
@Data
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @EqualsAndHashCode.Exclude
    private String name;
}
