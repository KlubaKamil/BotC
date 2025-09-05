package com.czachodym.BotC.model.util;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@MappedSuperclass
public abstract class BotCNameEntity extends BotCEntity{
    @Column
    private String name;
}
