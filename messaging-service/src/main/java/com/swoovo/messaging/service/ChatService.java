package com.swoovo.messaging.service;

import com.swoovo.messaging.dto.request.ChatRequest;
import com.swoovo.messaging.dto.response.ChatResponse;
import com.swoovo.messaging.entity.Chat;
import com.swoovo.messaging.mapper.ChatMapper;
import com.swoovo.messaging.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public ChatResponse saveChat(ChatRequest request) {
        Chat chat = chatRepository.save(chatMapper.fromChatRequest(request));

        return chatMapper.toChatResponse(chat);
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> findAllChats() {
        return chatRepository.findAll().stream()
                .map(chatMapper::toChatResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> findChatsByUserId(String userId) {
        return chatRepository.findAll().stream()
                .filter(chat -> (chat.getFirstUserId() != null && chat.getFirstUserId().equals(userId)) ||
                        (chat.getSecondUserId() != null && chat.getSecondUserId().equals(userId)))
                .map(chatMapper::toChatResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteChat(long id) throws EntityNotFoundException {
        if (!chatRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        chatRepository.deleteById(id);
    }
}
