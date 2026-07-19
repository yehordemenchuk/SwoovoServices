package com.swoovo.announcements.controller;

import com.swoovo.announcements.dto.request.LikeRequest;
import com.swoovo.announcements.dto.response.LikeStatusResponse;
import com.swoovo.announcements.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes/")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeStatusResponse> toggleLike(@RequestBody LikeRequest likeRequest) {
        return ResponseEntity.ok(likeService.addLike(likeRequest));
    }

    @GetMapping
    public ResponseEntity<LikeStatusResponse> getLikeStatus(
            @RequestParam long id,
            @RequestParam long userId) {
        return ResponseEntity.ok(likeService.getLikeStatus(id, userId));
    }
}

