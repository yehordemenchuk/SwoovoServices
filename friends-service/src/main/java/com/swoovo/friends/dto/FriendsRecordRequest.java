package com.swoovo.friends.dto;

import jakarta.validation.constraints.Positive;

public record FriendsRecordRequest(@Positive Long firstUserId,
                                   @Positive Long secondUserId) {
}
