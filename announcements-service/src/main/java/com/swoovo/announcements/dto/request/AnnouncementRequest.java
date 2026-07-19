package com.swoovo.announcements.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AnnouncementRequest(@NotEmpty String title,
                                  @NotEmpty String content,
                                  @NotEmpty String creatorId,
                                  @NotNull MultipartFile image) {
}
