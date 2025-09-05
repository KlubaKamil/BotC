package com.czachodym.BotC.config;

import com.czachodym.BotC.model.User;
import com.czachodym.BotC.model.util.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Slf4j
public class Config {
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    @RequestScope
    public CurrentUser availableGroups() {
        User user = (User)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return CurrentUser.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .groupRoles(user.getGroupRoles())
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
