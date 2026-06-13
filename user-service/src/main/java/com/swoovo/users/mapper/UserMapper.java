package com.swoovo.users.mapper;

import com.swoovo.users.dto.UserRequest;
import com.swoovo.users.dto.UserResponse;
import com.swoovo.users.entity.UserEntity;
import com.swoovo.users.service.MinioService;
import org.mapstruct.*;

import java.io.IOException;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "avatarFilePath", expression = "java(mapAvatar(userRequest, minioService))")
    @Mapping(target = "deviceTokens", ignore = true)
    UserEntity fromRequest(UserRequest userRequest);

    @Mapping(target = "avatarUrl", expression = "java(mapAvatarFilePath(userEntity, minioService))")
    UserResponse toResponse(UserEntity userEntity);

    @Mapping(target = "deviceTokens", ignore = true)
    void updateUserFromRequest(UserRequest userRequest, @MappingTarget UserEntity userEntity);

    private String mapAvatar(UserRequest userRequest, @Context MinioService minioService) throws IOException {
        minioService.uploadFile(userRequest.avatar().getName(),
                userRequest.avatar().getInputStream(),
                userRequest.avatar().getSize(),
                userRequest.avatar().getContentType());

        return userRequest.avatar().getOriginalFilename();
    }

    private String mapAvatarFilePath(UserEntity userEntity, @Context MinioService minioService) throws IOException {
        return minioService.downloadFile(userEntity.getAvatarFilePath());
    }

    @AfterMapping
    private void addDeviceToken(UserRequest userRequest,
                                @MappingTarget UserEntity userEntity) {
        if (Objects.nonNull(userEntity.getDeviceTokens()))
            userEntity.getDeviceTokens().add(userRequest.deviceToken());
    }
}
