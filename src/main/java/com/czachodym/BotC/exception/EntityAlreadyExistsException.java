package com.czachodym.BotC.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String name){
        super(name);
    }
}
