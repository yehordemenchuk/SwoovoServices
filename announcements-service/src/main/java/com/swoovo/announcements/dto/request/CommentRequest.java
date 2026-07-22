package com.swoovo.announcements.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CommentRequest(@NotNull @Positive Long announcementId,
                             @NotNull @Positive Long userId,
                             @NotEmpty String text,

                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             @NotNull @PastOrPresent
                             LocalDateTime createdAt) {
}
