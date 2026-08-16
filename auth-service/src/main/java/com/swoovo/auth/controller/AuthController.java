package com.swoovo.auth.controller;

import com.swoovo.auth.dto.GoogleRequest;
import com.swoovo.auth.dto.LoginRequest;
import com.swoovo.auth.dto.RefreshRequest;
import com.swoovo.auth.dto.TokenResponse;
import com.swoovo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }

    @PostMapping("login-google")
    public ResponseEntity<TokenResponse> loginWithGoogle(@RequestBody GoogleRequest googleRequest) {
        return ResponseEntity.ok(authService.loginWithGoogle(googleRequest));
    }
}
