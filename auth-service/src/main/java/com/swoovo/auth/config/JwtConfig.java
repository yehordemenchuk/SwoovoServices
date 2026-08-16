package com.swoovo.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swoovo.support.util.JwtUtil;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token.expiration_time}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh_token.expiration_time}")
    private long refreshTokenExpirationTime;

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil(secret, accessTokenExpirationTime, refreshTokenExpirationTime);
    }
}
