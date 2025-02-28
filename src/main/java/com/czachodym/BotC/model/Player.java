package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.BotCEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class Player extends BotCEntity implements Serializable {
}
