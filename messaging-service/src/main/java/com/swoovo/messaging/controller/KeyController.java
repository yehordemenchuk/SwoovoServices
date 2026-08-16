package com.swoovo.messaging.controller;

import com.swoovo.messaging.dto.request.KeyBundleRequest;
import com.swoovo.messaging.dto.response.PreKeyResponse;
import com.swoovo.messaging.service.KeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats/")
@RequiredArgsConstructor
public class KeyController {
    private final KeyService keyService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid KeyBundleRequest bundle) {
        keyService.registerPreKeys(bundle);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PreKeyResponse> fetch(@RequestParam String userId,
                                                @RequestParam int deviceId) {
        PreKeyResponse response = keyService.consumePreKeys(userId, deviceId);

        return ResponseEntity.ok(response);
    }
}
