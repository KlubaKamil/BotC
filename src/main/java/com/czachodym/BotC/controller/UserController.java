package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.JwtRequest;
import com.czachodym.BotC.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtRequest jwtRequest){
        log.info("Trying to log in.");
        String jwt = jwtService.authenticate(jwtRequest);
        log.info("Logging in successful.");
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
