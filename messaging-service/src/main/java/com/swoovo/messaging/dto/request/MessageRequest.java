package com.swoovo.messaging.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record MessageRequest(@NotNull @Positive Long chatId,
                             @NotNull @Positive Long senderId,
                             @NotEmpty String content,
                             @NotEmpty String messageType,
                             @NotEmpty String timestamp,
                             @NotNull List<MultipartFile> attachments) {
}
