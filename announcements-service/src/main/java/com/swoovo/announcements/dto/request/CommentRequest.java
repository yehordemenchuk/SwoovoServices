package com.swoovo.announcements.dto.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CommentRequest(@NotNull @Positive Long announcementId,
                             @NotNull @Positive Long userId,
                             @NotEmpty @Size(max=2000) String text,

                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             @NotNull @PastOrPresent
                             LocalDateTime createdAt) {
}
