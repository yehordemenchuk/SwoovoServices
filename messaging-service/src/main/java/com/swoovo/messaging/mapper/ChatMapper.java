package com.swoovo.messaging.mapper;

import com.swoovo.messaging.dto.request.ChatRequest;
import com.swoovo.messaging.dto.response.ChatResponse;
import com.swoovo.messaging.entity.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat fromChatRequest(ChatRequest chatRequest);

    ChatResponse toChatResponse(Chat chat);
}
