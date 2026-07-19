package com.swoovo.announcements.dto.request.toggling;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequest(@NotEmpty Long userId,
                          @NotNull @Positive Long announcementId) implements TogglingEntityRequest {
}
