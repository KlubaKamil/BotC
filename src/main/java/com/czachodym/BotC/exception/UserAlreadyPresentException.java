package com.czachodym.BotC.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyPresentException extends RuntimeException{
    public UserAlreadyPresentException(String role){
        super(role);
    }
}
