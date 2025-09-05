package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.BotCNameEntity;
import com.czachodym.BotC.model.util.ScriptCharacter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Script extends BotCNameEntity implements Serializable {
    @Column
    private String author;
    @Lob
    private String notes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScriptCharacter> scriptCharacters;
}
