package com.swoovo.complaints.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record ComplaintRequest(@NotNull @Positive Long userId,
                               @NotEmpty @Size(max=2000) String text,
                               @NotNull List<MultipartFile> images,

                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               @NotNull @PastOrPresent
                               LocalDateTime createdAt) {
}
