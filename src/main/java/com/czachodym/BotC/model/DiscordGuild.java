package com.czachodym.BotC.model;

import com.czachodym.BotC.model.util.BotCEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DiscordGuild extends BotCEntity{
    @Column
    String discordGuildId;
}
