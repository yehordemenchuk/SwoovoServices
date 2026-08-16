package com.swoovo.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swoovo.support.util.DiscoveryClientUtil;
import org.swoovo.support.util.WebRequestUtil;

@Configuration
@RequiredArgsConstructor
public class DiscoveryClientConfig {
    private final DiscoveryClient discoveryClient;
    private final WebRequestUtil webRequestUtil;

    @Bean
    public DiscoveryClientUtil getDiscoveryClientUtil() {
        return new DiscoveryClientUtil(discoveryClient, webRequestUtil);
    }
}
