package com.swoovo.messaging.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRequest(@NotNull @Positive Long firstUserId,
                          @NotNull @Positive Long secondUserId) {
}
