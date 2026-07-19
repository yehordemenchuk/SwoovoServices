package com.swoovo.users.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Data
public final class UserResponse {
    private final Long id;
    private final String name;
    private final String surname;
    private final String email;
    private final BigInteger phoneNumber;
    private final String userRole;
    private final Integer age;
    private final List<String> deviceTokens;

    private String avatarUrl;
}
