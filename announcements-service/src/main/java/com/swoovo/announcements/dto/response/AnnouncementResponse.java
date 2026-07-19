package com.swoovo.announcements.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public final class AnnouncementResponse {
    private final Long id;
    private final String title;
    private final String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private final LocalDateTime createdAt;

    private final String creatorId;

    private String imageUrl;
}
