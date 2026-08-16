package com.swoovo.auth.dto;

public record GoogleUser(String googleId,
                         String email,
                         String name,
                         String avatar) {
}
