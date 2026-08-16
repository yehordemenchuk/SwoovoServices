package com.swoovo.auth.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "keycloak")
public final class KeycloakProperties {
    private String serverUrl;

    private String adminRealm;

    private String appRealm;

    private String adminUsername;

    private String adminPassword;

    private String clientId;

    public Map<String, String> toMap() {
        return Map.of("serverUrl", serverUrl,
                "adminRealm", adminRealm,
                "appRealm", appRealm,
                "adminUsername", adminUsername,
                "adminPassword", adminPassword,
                "clientId", clientId);
    }
}
