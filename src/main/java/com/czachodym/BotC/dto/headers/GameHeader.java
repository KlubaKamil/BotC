package com.czachodym.BotC.dto.headers;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record GameHeader(
    long id,
    String scriptName,
    Object storytellerName,
    int playersNumber,
    boolean goodWon,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate date
){
    public GameHeader(long id, String scriptName, Object storytellerName, int playersNumber, boolean goodWon, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate date) {
        this.id = id;
        this.scriptName = scriptName;
        this.storytellerName = storytellerName.toString().replaceAll(",", ", ");
        this.playersNumber = playersNumber;
        this.goodWon = goodWon;
        this.date = date;
    }
}
