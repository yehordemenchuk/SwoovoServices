package com.swoovo.posts.dto;

import lombok.Data;
import java.util.List;

@Data
public final class PostResponse {
    private Long id;
    private String name;
    private String postText;
    private List<String> mediaFileNames;
    private Long userId;
    private List<String> mediaUrls;
}
