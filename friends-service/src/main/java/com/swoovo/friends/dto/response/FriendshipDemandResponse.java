package com.swoovo.friends.dto.response;

import java.time.LocalDateTime;

public record FriendshipDemandResponse(Long id,
                                       Long senderId,
                                       Long receiverId,
                                       String status,
                                       LocalDateTime createdAt) {
}
