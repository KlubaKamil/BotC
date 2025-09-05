package com.czachodym.BotC.controller;

import com.czachodym.BotC.dto.JwtRequest;
import com.czachodym.BotC.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtRequest jwtRequest){
        log.info("Trying to log in.");
        Map<String, String> map = authenticationService.authenticate(jwtRequest);
        log.info("Logging in successful.");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/login/discord/{code}")
    public ResponseEntity<Map<String, String>> loginWithDiscord(@PathVariable String code){
        log.info("Trying to log in with discord.");
        Map<String, String> map = authenticationService.authenticateWithDiscord(code);
        log.info("Logging in successful.");
        return ResponseEntity.ok(map);
    }
}
