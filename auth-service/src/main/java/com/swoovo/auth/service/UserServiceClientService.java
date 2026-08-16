package com.swoovo.auth.service;

import com.swoovo.auth.dto.UserRequest;
import com.swoovo.auth.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.swoovo.support.util.DiscoveryClientUtil;
import org.swoovo.support.util.WebRequestUtil;
import org.swoovo.support.util.helpers.AppServicesName;

@Service
@RequiredArgsConstructor
public class UserServiceClientService {
    private final DiscoveryClientUtil discoveryClientUtil;
    private final WebRequestUtil webRequestUtil;

    public UserResponse callUserServiceGetByUsername(String username) {
        return discoveryClientUtil.callGetMethod(AppServicesName.USER_SERVICE,
                "/api/v1/users/by-email/" + username, UserResponse.class);
    }

    public UserResponse callUserServiceCreateUser(UserRequest userRequest) {
        return discoveryClientUtil.callPostMethod(AppServicesName.USER_SERVICE,
                "/api/v1/users/", userRequest,
                UserResponse.class, MediaType.APPLICATION_JSON);
    }
}
