package com.czachodym.BotC.model.util;

import com.czachodym.BotC.model.Group;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@MappedSuperclass
public abstract class BotCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ElementCollection
    @Builder.Default
    private Set<Group> groups = new HashSet<>();
}
