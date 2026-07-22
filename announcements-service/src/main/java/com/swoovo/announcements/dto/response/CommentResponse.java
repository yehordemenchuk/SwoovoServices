package com.swoovo.announcements.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CommentResponse(Long id,
                              Long userId,
                              String text,

                              @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                              LocalDateTime createdAt) {
}
