package com.swoovo.friends.service;

import com.swoovo.friends.dto.request.FriendsRecordRequest;
import com.swoovo.friends.dto.response.FriendsRecordResponse;
import com.swoovo.friends.entities.FriendsRecordEntity;
import com.swoovo.friends.entities.FriendshipDemandEntity;
import com.swoovo.friends.mapper.FriendsRecordMapper;
import com.swoovo.friends.repository.FriendsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRecordRepository;
    private final FriendsRecordMapper friendsRecordMapper;

    @Caching(evict = {
            @CacheEvict(key = "#friendRequest.firstUserId()", value = "friends_ids"),
            @CacheEvict(key = "#friendRequest.secondUserId()", value = "friends_ids")
    })
    public FriendsRecordResponse createFriend(FriendsRecordRequest friendRequest) {
        FriendsRecordEntity friendRecord = friendsRecordRepository.save(friendsRecordMapper
                .toEntity(friendRequest));

        return friendsRecordMapper.toResponse(friendRecord);
    }

    @Cacheable(key="#id", value = "friends")
    public FriendsRecordResponse findById(long id) throws EntityNotFoundException {
        FriendsRecordEntity friendRecord = friendsRecordRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return friendsRecordMapper.toResponse(friendRecord);
    }

    @Cacheable(key = "#userId", value = "friends_ids")
    public List<Long> findAllForUser(long userId) {
        return friendsRecordRepository.findAll()
                .stream()
                .filter(f -> f.getFirstUserId() == userId
                        || f.getSecondUserId() == userId)
                .map(f -> {
                    if (f.getFirstUserId() == userId)
                        return f.getSecondUserId();

                    return f.getFirstUserId();
                })
                .toList();
    }

    public Page<FriendsRecordResponse> findAll(Pageable pageable) {
        return friendsRecordRepository.findAll(pageable).map(friendsRecordMapper::toResponse);
    }

    @Caching(evict = {
            @CacheEvict(key = "#friendshipDemandEntity.receiverId", value = "friends_ids"),
            @CacheEvict(key = "#friendshipDemandEntity.senderId", value = "friends_ids")
    })
    void createFromDemand(FriendshipDemandEntity friendshipDemandEntity) {
        boolean friendshipExists = friendsRecordRepository.findAll().stream()
                .anyMatch(f ->
                        (f.getFirstUserId().equals(friendshipDemandEntity.getSenderId())
                                && f.getSecondUserId().equals(friendshipDemandEntity.getReceiverId())) ||
                                (f.getFirstUserId().equals(friendshipDemandEntity.getReceiverId())
                                        && f.getSecondUserId().equals(friendshipDemandEntity.getSenderId()))
                );

        if (!friendshipExists) {
            friendsRecordRepository.save(friendsRecordMapper.fromDemand(friendshipDemandEntity));
        }
    }
}
