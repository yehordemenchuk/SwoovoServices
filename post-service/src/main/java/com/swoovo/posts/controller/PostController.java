package com.swoovo.posts.controller;

import com.swoovo.posts.dto.PostRequest;
import com.swoovo.posts.dto.PostResponse;
import com.swoovo.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostResponse> createPost(@ModelAttribute PostRequest postRequest) throws IOException {
        PostResponse postResponse = postService.createPost(postRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(postService.findByUserId(userId, pageable));
    }
}
