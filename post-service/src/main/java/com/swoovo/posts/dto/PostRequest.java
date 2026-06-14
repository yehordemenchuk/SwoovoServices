package com.swoovo.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostRequest(@NotBlank String name,
                          @NotBlank String postText,
                          @NotNull List<MultipartFile> media,
                          @Positive Long userId) {
}
