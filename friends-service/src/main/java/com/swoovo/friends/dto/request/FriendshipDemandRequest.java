package com.swoovo.friends.dto.request;

import com.swoovo.friends.entities.RequestStatus;

import java.time.LocalDateTime;

public record FriendshipDemandRequest(Long senderId,
                                      Long receiverId,
                                      RequestStatus status,
                                      LocalDateTime createdAt) {
}
