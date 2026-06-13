package com.swoovo.users.mapper;

import com.swoovo.users.dto.UserRequest;
import com.swoovo.users.dto.UserResponse;
import com.swoovo.users.entity.UserEntity;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "avatarFilePath", expression = "java(mapAvatar(userRequest))")
    @Mapping(target = "deviceTokens", ignore = true)
    UserEntity fromRequest(UserRequest userRequest);

    UserResponse toResponse(UserEntity userEntity);

    @Mapping(target = "deviceTokens", ignore = true)
    void updateUserFromRequest(UserRequest userRequest, @MappingTarget UserEntity userEntity);

    default String mapAvatar(UserRequest userRequest) {
        return userRequest.avatar().getOriginalFilename();
    }

    @AfterMapping
    default void addDeviceToken(UserRequest userRequest,
                                @MappingTarget UserEntity userEntity) {
        if (Objects.nonNull(userEntity.getDeviceTokens()))
            userEntity.getDeviceTokens().add(userRequest.deviceToken());
    }
}
