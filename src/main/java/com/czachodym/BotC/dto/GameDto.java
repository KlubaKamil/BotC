package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.util.AssignmentDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GameDto (
        Long id,
        ScriptDto script,
        PlayerDto storyteller,
        CharacterDto fabled,
        List<AssignmentDto> assignments,
        boolean goodWon,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date,
        String notes,
        PlaceDto place,
        boolean imageUploaded,
        List<Integer> balanceMarks
){}
