package com.swoovo.posts.dto;

import java.util.ArrayList;
import java.util.List;

public record PostResponse(Long id,
                           String name,
                           String postText,
                           List<String> mediaFileNames,
                           Long userId,
                           List<String> mediaUrls) {
    public PostResponse {
        mediaUrls = new ArrayList<>();
    }
}
