package com.swoovo.auth.dto;

public record TokenResponse(String accessToken,
                            String refreshToken,
                            Long expiresIn) {
}
