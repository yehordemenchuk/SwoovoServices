package com.swoovo.announcements.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequest(@NotEmpty String userId,
                          @NotNull @Positive Long announcementId) {
}
