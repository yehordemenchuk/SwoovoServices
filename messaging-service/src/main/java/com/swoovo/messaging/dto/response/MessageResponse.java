package com.swoovo.messaging.dto.response;

import java.util.List;

public record MessageResponse(Long id,
                              Long chatId,
                              String senderId,
                              String content,
                              String messageType,
                              String timestamp,
                              List<String> attachmentsUrls) {
}
