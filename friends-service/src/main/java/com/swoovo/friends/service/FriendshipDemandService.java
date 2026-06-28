package com.swoovo.friends.service;

import com.swoovo.friends.dto.request.FriendshipDemandRequest;
import com.swoovo.friends.dto.response.FriendshipDemandResponse;
import com.swoovo.friends.entities.FriendshipDemandEntity;
import com.swoovo.friends.entities.RequestStatus;
import com.swoovo.friends.mapper.FriendshipDemandMapper;
import com.swoovo.friends.repository.FriendshipDemandRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipDemandService {
    private final FriendshipDemandRepository friendshipDemandRepository;
    private final FriendshipDemandMapper friendshipDemandMapper;
    private final FriendsService friendsService;

    @Transactional
    @CacheEvict(value = "demands", allEntries = true)
    public FriendshipDemandResponse createFriendDemand(FriendshipDemandRequest friendshipDemandRequest) {
        if (checkIfFriendshipDemandExists(friendshipDemandRequest.senderId(),
                friendshipDemandRequest.receiverId())) {
            throw new EntityExistsException("Friend request already exists");
        }


        FriendshipDemandEntity entity = friendshipDemandRepository.save(friendshipDemandMapper
                .fromRequest(friendshipDemandRequest));

        return friendshipDemandMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId", value = "demands")
    public List<FriendshipDemandResponse> getPendingDemandsForUser(long userId) {
        List<FriendshipDemandEntity> requests = friendshipDemandRepository.findByReceiverIdAndStatus(
                userId,
                RequestStatus.PENDING
        );

        return requests.stream()
                .map(friendshipDemandMapper::toResponse)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "demands", allEntries = true)
    public FriendshipDemandResponse changeDemandStatus(long id, RequestStatus newStatus) {
        FriendshipDemandEntity demand = friendshipDemandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Friend request not found"));

        if (!RequestStatus.PENDING.equals(demand.getStatus())) {
            throw new IllegalArgumentException("Request is not pending. Current status: " + demand.getStatus());
        }

        demand.setStatus(newStatus);

        friendshipDemandRepository.save(demand);

        if (newStatus.equals(RequestStatus.ACCEPTED))
            friendsService.createFromDemand(friendshipDemandRepository.save(demand));

        return friendshipDemandMapper.toResponse(demand);
    }

    private boolean checkIfFriendshipDemandExists(long senderId, long receiverId) {
        return friendshipDemandRepository.existsBySenderIdAndReceiverIdAndStatus(
                senderId,
                receiverId,
                RequestStatus.PENDING
        );
    }
}
