package com.czachodym.BotC.service;

import com.czachodym.BotC.dto.JwtRequest;
import com.czachodym.BotC.model.User;
import com.czachodym.BotC.model.util.DiscordUser;
import com.czachodym.BotC.service.jwt.JwtService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    @Value("${discord.token-url}")
    private String DISCORD_TOKEN_URL;
    @Value("${discord.user-url}")
    private String DISCORD_USER_URL;
    @Value("${discord.clientId}")
    private String CLIENT_ID;
    @Value("${discord.secret}")
    private String SECRET;
    @Value("${discord.redirect-uri}")
    private String REDIRECT_URI;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public Map<String, String>  authenticate(JwtRequest jwtRequest){
        final String login = "user";
        final String password = jwtRequest.password();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, password));
        User user = (User) authentication.getPrincipal();
        String token = jwtService.authenticate(user);
        return Map.of("token", token);
    }

    public Map<String, String> authenticateWithDiscord(String code) {
        DiscordToken discordToken = getDiscordAccessToken(code);
        String accessToken = discordToken.accessToken;
        DiscordUser discordUser = getDiscordUser(accessToken);
        Optional<User> userOptional = userService.loadUserByDiscordId(discordUser.id());
        User user;
        if(userOptional.isEmpty()){
            user = userService.createUserWithDiscord(discordUser.id(), discordUser.globalName());
        } else {
            user = userOptional.get();
        }
        String token = jwtService.authenticate(user);
        return Map.of("token", token);
    }

    private DiscordUser getDiscordUser(String token){
        log.info("Trying to fetch discord user with access token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<DiscordUser> response = restTemplate.exchange(
                DISCORD_USER_URL,
                HttpMethod.GET,
                entity,
                DiscordUser.class
        );
        DiscordUser discordUser = response.getBody();

        log.info("User fetched: id: {}, username: {}, globalName: {}",
                discordUser.id(), discordUser.username(), discordUser.globalName());
        return discordUser;
    }

    private DiscordToken getDiscordAccessToken(String code){
        log.info("Trying to get discord access token with code.");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + CLIENT_ID +
                "&client_secret=" + SECRET +
                "&grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=" + REDIRECT_URI;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<DiscordToken> response = restTemplate.exchange(
                DISCORD_TOKEN_URL,
                HttpMethod.POST,
                entity,
                DiscordToken.class
        );
        log.info("Access token received");
        return response.getBody();
    }

    record DiscordToken(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") long expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("scope") String scope
    ){}
}
