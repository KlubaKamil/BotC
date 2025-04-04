package com.czachodym.BotC.dto.headers;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record GameHeader(
    long id,
    String scriptName,
    String storytellerName,
    int playersNumber,
    boolean goodWon,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate date
){}
