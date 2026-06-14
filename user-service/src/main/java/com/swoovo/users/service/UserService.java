package com.swoovo.users.service;

import com.swoovo.users.dto.UserRequest;
import com.swoovo.users.dto.UserResponse;
import com.swoovo.users.entity.UserEntity;
import com.swoovo.users.mapper.UserMapper;
import com.swoovo.users.repository.UserEntityRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swoovo.support.util.MinioUtil;

import java.io.IOException;
import java.io.UncheckedIOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;
    private final MinioUtil minioUtil;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", allEntries = true)
    })
    public UserResponse createUser(UserRequest userRequest)
            throws EntityExistsException {
        if (checkUserExists(userRequest)) {
            throw new EntityExistsException("User with this data already exists: " + userRequest);
        }

        uploadUserAvatar(userRequest);

        UserEntity userEntity = userEntityRepository
                .save(userMapper.fromRequest(userRequest));

        return getUserResponse(userEntity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user")
    public UserResponse findUserById(long id) {
        return getUserResponse(findUserEntityById(id));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "users")
    public Page<UserResponse> findAllUsers(Pageable pageable) {
        return userEntityRepository.findAll(pageable).map(this::getUserResponse);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#id")
    })
    public UserResponse updateUser(long id, UserRequest userRequest) {
        UserEntity userEntity = findUserEntityById(id);

        userMapper.updateUserFromRequest(userRequest, userEntity);

        return getUserResponse(userEntityRepository.save(userEntity));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#id")
    })
    public void deleteUserById(long id) throws EntityNotFoundException {
        if (!userEntityRepository.existsById(id)) {
            throw new EntityNotFoundException(getUserNotFoundByIdException(id));
        }

        userEntityRepository.deleteById(id);
    }

    private boolean checkUserExists(UserRequest userRequest) {
        return userEntityRepository.findByNameAndSurname(userRequest.name(),
                userRequest.surname()).isPresent()
                || userEntityRepository.findByEmail(userRequest.email())
                .isPresent() || userEntityRepository
                .findByPhoneNumber(userRequest.phoneNumber()).isPresent();
    }

    private UserResponse getUserResponse(UserEntity userEntity) {
        UserResponse userResponse = userMapper.toResponse(userEntity);

        userResponse.setAvatarUrl(minioUtil.downloadFile(userEntity.getAvatarFilePath()));

        return userResponse;
    }

    private UserEntity findUserEntityById(long id) throws EntityNotFoundException {
        return userEntityRepository.findById(id)
                .orElseThrow(() -> getUserNotFoundByIdException(id));
    }

    private void uploadUserAvatar(UserRequest userRequest) throws UncheckedIOException {
        try {
            minioUtil.uploadFile(userRequest.avatar().getName(),
                    userRequest.avatar().getInputStream(),
                    userRequest.avatar().getSize(),
                    userRequest.avatar().getContentType());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private EntityNotFoundException getUserNotFoundByIdException(long id) {
        return new EntityNotFoundException("User with id " + id + " not found");
    }
}
