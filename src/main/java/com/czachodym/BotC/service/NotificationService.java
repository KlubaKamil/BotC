package com.czachodym.BotC.service;

import com.czachodym.BotC.model.util.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    public void notifyDiscordService(NotificationType type, long id){
        //XD nie sent
    }
}
