package com.swoovo.announcements.dto.request;

public record CommentRequest(Long announcementId,
                             String userId,
                             String text) {
}
