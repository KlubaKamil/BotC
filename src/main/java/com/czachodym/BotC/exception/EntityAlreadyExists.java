package com.czachodym.BotC.exception;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String name){
        super(name);
    }
}
