package com.swoovo.announcements.controller;

import com.swoovo.announcements.dto.request.SavingRequest;
import com.swoovo.announcements.dto.response.SavingStatusResponse;
import com.swoovo.announcements.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savings/")
@RequiredArgsConstructor
public class SavingController {
    private final SavingService savingService;

    @PostMapping
    public ResponseEntity<SavingStatusResponse> createSaving(@RequestBody SavingRequest savingRequest) {
        return ResponseEntity.ok(savingService.addSaving(savingRequest));
    }

    @GetMapping
    public ResponseEntity<SavingStatusResponse> getSaveStatus(
            @RequestParam long id,
            @RequestParam long userId) {
        return ResponseEntity.ok(savingService.getSaveStatus(id, userId));
    }
}
