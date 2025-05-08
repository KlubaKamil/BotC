package com.czachodym.BotC.service.jwt;

import com.czachodym.BotC.dto.JwtRequest;
import com.czachodym.BotC.service.UserService;
import com.czachodym.BotC.service.jwt.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public String authenticate(JwtRequest authenticationRequest){
        final String login = "user";
        final String password = authenticationRequest.password();

        log.info("Validating authentication.");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        log.info("Validation successful, generating token.");
        final UserDetails userDetails = userService.loadUserByUsername(login);
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info("Token created.");
        return token;
    }
}
