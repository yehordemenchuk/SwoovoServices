package com.swoovo.auth.dto;

import java.math.BigInteger;
import java.util.List;

public record UserResponse(Long id,
                           String name,
                           String surname,
                           String email,
                           BigInteger phoneNumber,
                           String userRole,
                           Integer age,
                           List<String> deviceTokens,
                           String avatarUrl) {
}
