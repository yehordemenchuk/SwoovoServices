package com.swoovo.announcements.controller;

import com.swoovo.announcements.dto.request.CommentRequest;
import com.swoovo.announcements.dto.response.CommentResponse;
import com.swoovo.announcements.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addComment(commentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commentResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(commentResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable long id) {
        return ResponseEntity.ok(commentService.findAllComments(id));
    }
}
