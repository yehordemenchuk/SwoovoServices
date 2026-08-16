package com.swoovo.messaging.dto.response;

public record ChatResponse(Long id,
                           String firstUserId,
                           String secondUserId) {
}
