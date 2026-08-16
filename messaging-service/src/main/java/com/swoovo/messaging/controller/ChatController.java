package com.swoovo.messaging.controller;

import com.swoovo.messaging.dto.request.ChatRequest;
import com.swoovo.messaging.dto.response.ChatResponse;
import com.swoovo.messaging.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats/")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> createChat(@RequestBody @Valid ChatRequest chatRequest) {
        ChatResponse chatResponse = chatService.saveChat(chatRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(chatResponse);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<ChatResponse>> getChatsByUserId(@PathVariable String userId) {
        List<ChatResponse> chats = chatService.findChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllChats() {
        List<ChatResponse> chats = chatService.findAllChats();
        return ResponseEntity.ok(chats);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable long id) {
        chatService.deleteChat(id);

        return ResponseEntity.noContent().build();
    }
}
