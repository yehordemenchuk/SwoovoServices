package com.swoovo.posts.dto;

import lombok.Data;

import java.util.List;

@Data
public final class PostResponse {
    private final Long id;
    private final String name;
    private final String postText;
    private final List<String> mediaFileNames;
    private final Long userId;
    private List<String> mediaUrls;
}
