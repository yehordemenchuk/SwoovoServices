package com.swoovo.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.swoovo.support.util.WebRequestUtil;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final WebClient webClient;

    @Bean
    public WebRequestUtil getWebRequestUtil() {
        return new WebRequestUtil(webClient);
    }
}
