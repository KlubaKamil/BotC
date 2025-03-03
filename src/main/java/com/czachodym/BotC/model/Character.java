package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.BotCEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "character_")
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Character extends BotCEntity implements Serializable {
    @Column
    @Enumerated(EnumType.STRING)
    private Alignment alignment;
    @Column
    private String description;
    @Column
    private String linkToWiki;

}
