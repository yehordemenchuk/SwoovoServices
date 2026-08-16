package com.swoovo.auth.config;

import com.swoovo.auth.props.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swoovo.support.util.KeycloakUtil;
import org.swoovo.support.util.WebRequestUtil;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    private final KeycloakProperties keycloakProperties;
    private final WebRequestUtil webRequestUtil;

    @Bean
    public KeycloakUtil getKeycloakUtil() {
        return new KeycloakUtil(keycloakProperties.toMap(), webRequestUtil);
    }
}
