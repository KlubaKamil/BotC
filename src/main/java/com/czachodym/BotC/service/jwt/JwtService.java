package com.czachodym.BotC.service.jwt;

import com.czachodym.BotC.model.User;
import com.czachodym.BotC.service.jwt.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtTokenUtil jwtTokenUtil;

    public String authenticate(User user){
        log.info("Generating token...");
        String token = jwtTokenUtil.generateToken(user);
        log.info("Token created.");
        return token;
    }
}
