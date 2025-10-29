package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.util.AssignmentDto;
import com.czachodym.BotC.dto.util.BalanceMarkDto;
import com.czachodym.BotC.model.Group;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
public record GameDto (
        long id,
        Set<Group> groups,
        ScriptDto script,
        List<PlayerDto> storytellers,
        List<CharacterDto> fables,
        List<AssignmentDto> assignments,
        boolean goodWon,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date,
        String notes,
        PlaceDto place,
        boolean imageUploaded,
        Set<BalanceMarkDto> balanceMarks
){}
