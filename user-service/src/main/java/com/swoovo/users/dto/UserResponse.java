package com.swoovo.users.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Data
public final class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private BigInteger phoneNumber;
    private String userRole;
    private Integer age;
    private List<String> deviceTokens;

    @Setter
    private String avatarUrl;
}
