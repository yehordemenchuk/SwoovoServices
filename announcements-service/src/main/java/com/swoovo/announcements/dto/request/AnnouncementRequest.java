package com.swoovo.announcements.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record AnnouncementRequest(@NotEmpty String title,
                                  @NotEmpty String content,
                                  @NotNull @Positive Long userId,
                                  @NotNull MultipartFile image,

                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                  @NotNull @PastOrPresent
                                  LocalDateTime createdAt) {
}
