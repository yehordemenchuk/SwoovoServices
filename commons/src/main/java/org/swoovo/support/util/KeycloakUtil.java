package org.swoovo.support.util;

import lombok.Setter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Map;
import java.util.Objects;

@Setter
public final class KeycloakUtil {
    private final Map<String, String> properties;
    private WebRequestUtil webRequestUtil;

    public KeycloakUtil(Map<String, String> properties, WebRequestUtil webRequestUtil) {
        this.properties = properties;

        this.webRequestUtil = webRequestUtil;
    }

    public Keycloak getRegistrationKeycloak() {
        return getKeycloakBuildingBasement(true, "",
                properties.get("adminUsername"),
                properties.get("adminPassword")).build();
    }

    public Keycloak getLoginKeycloak(String username, String password) {
        return getKeycloakBuildingBasement(false, OAuth2Constants.PASSWORD,
                username, password)
                .build();
    }

    public Keycloak getRefreshKeycloak() {
        return getKeycloakBuildingBasement(false, OAuth2Constants.REFRESH_TOKEN)
                .build();

    }

    public <T> T sendRefreshRequest(String refreshToken, Class<T> clazz) {
        return webRequestUtil.post(properties.get("serverUrl")
                + "/realms/"
                + properties.get("appRealm")
                + "/protocol/openid-connect/token", BodyInserters.fromFormData("grant_type", OAuth2Constants.REFRESH_TOKEN)
                .with("client_id", properties.get("clientId"))
                .with("refresh_token", refreshToken), clazz,
                MediaType.APPLICATION_FORM_URLENCODED);
    }

    private KeycloakBuilder getKeycloakBuildingBasement(boolean isAdminRealm, String grantType,
                                                        String username,
                                                        String password) {
        return getKeycloakBuildingBasement(isAdminRealm, grantType)
                .username(username)
                .password(password);
    }

    private KeycloakBuilder getKeycloakBuildingBasement(boolean isAdminRealm, String grantType) {
        KeycloakBuilder base = KeycloakBuilder.builder()
                .serverUrl(properties.get("serverUrl"))
                .realm(isAdminRealm ? properties.get("adminRealm") :
                        properties.get("appRealm"))
                .clientId(properties.get("clientId"));

        if (Objects.nonNull(grantType) && !grantType.isEmpty())
            base.grantType(grantType);

        return base;
    }
}
