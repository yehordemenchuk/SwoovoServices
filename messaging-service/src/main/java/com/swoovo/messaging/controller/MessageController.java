package com.swoovo.messaging.controller;

import com.swoovo.messaging.dto.request.MessageRequest;
import com.swoovo.messaging.dto.response.MessageResponse;
import com.swoovo.messaging.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/messages/")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MessageResponse> createMessage(@Valid @ModelAttribute MessageRequest messageRequest) {
        MessageResponse messageResponse = messageService.saveMessage(messageRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(messageResponse);
    }

    @GetMapping("{chatId}")
    public ResponseEntity<PagedModel<EntityModel<MessageResponse>>> getAllMessagesFromChat(@PathVariable long chatId, Pageable pageable,
                                                                                           PagedResourcesAssembler<MessageResponse> assembler) {
        Page<MessageResponse> page = messageService.findAllFromChat(pageable, chatId);

        return ResponseEntity.ok(assembler.toModel(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> readMessage(@PathVariable long id) {
        return ResponseEntity.ok(messageService.viewMessage(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable long id) {
        messageService.deleteMessage(id);

        return ResponseEntity.noContent().build();
    }
}
