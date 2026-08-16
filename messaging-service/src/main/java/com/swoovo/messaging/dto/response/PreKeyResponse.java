package com.swoovo.messaging.dto.response;

import java.time.Instant;

public record PreKeyResponse(Long id,
                             String userId,
                             int deviceId,
                             byte[] prekeyPub,
                             boolean used,
                             Instant createdAt) {
}
