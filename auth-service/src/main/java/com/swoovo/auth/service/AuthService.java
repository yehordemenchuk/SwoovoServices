package com.swoovo.auth.service;

import com.swoovo.auth.dto.*;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.swoovo.support.util.JwtUtil;
import org.swoovo.support.util.KeycloakUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KeycloakUtil keycloakUtil;
    private final JwtUtil jwtUtil;

    private final GoogleTokenService googleTokenService;
    private final UserServiceClientService userServiceClientService;

    public TokenResponse login(LoginRequest loginRequest) {
        AccessTokenResponse tokenResponse = keycloakUtil.getLoginKeycloak(loginRequest.username(),
                loginRequest.password()).tokenManager().getAccessToken();

        return new TokenResponse(tokenResponse.getToken(), tokenResponse.getRefreshToken(),
                tokenResponse.getExpiresIn());
    }

    public TokenResponse refresh(RefreshRequest refreshRequest) {
        return keycloakUtil.sendRefreshRequest(refreshRequest.refreshToken(),
                TokenResponse.class);
    }

    public TokenResponse loginWithGoogle(GoogleRequest googleRequest) {
        GoogleUser googleUser = googleTokenService.verify(googleRequest.googleToken());

        if (Objects.isNull(googleUser) ||
                Objects.nonNull(userServiceClientService
                        .callUserServiceGetByUsername(googleUser.email()))) {
            userServiceClientService.callUserServiceCreateUser(new UserRequest(
                    googleUser.name(),
                    googleUser.name(),
                    googleUser.email(),
                    null,
                    null,
                    null,
                    null,
                    null
            ));
        }

        return new TokenResponse(jwtUtil.generateAccessToken(googleUser.name()),
                                jwtUtil.generateRefreshToken(googleUser.name()),
                                jwtUtil.getRefreshTtlSeconds()
        );
    }
}
