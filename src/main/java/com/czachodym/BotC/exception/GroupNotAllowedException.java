
package com.czachodym.BotC.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GroupNotAllowedException extends RuntimeException{
    public GroupNotAllowedException(long groupId){
        super("Group id: " + groupId);
    }
}
