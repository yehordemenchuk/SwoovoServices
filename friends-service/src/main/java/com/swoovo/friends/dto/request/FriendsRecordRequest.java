package com.swoovo.friends.dto.request;

import jakarta.validation.constraints.Positive;

public record FriendsRecordRequest(@Positive Long firstUserId,
                                   @Positive Long secondUserId) {
}
