package com.czachodym.BotC.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(long id){
        super("Id: " + id);
    }

    public EntityNotFoundException(String name){
        super("Name: " + name);
    }

    public EntityNotFoundException(List<Long> requiredNames, List<Long> actualNames){
        super();
        log.error("Looked for: {}, but found: {}", requiredNames, actualNames);
    }
}
