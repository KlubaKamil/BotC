package com.czachodym.BotC.model.util;

import com.czachodym.BotC.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@ToString(callSuper = true)
@RequiredArgsConstructor
public class GroupRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private long id;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
}
