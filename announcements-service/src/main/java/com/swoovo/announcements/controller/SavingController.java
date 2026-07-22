package com.swoovo.announcements.controller;

import com.swoovo.announcements.dto.request.toggling.SavingRequest;
import com.swoovo.announcements.dto.response.SavingStatusResponse;
import com.swoovo.announcements.service.toggling.SavingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savings")
@RequiredArgsConstructor
public class SavingController {
    private final SavingService savingService;

    @PostMapping
    public ResponseEntity<SavingStatusResponse> toggleSavings(@Valid @RequestBody SavingRequest savingRequest) {
        return ResponseEntity.ok(savingService.toggleSaving(savingRequest));
    }

    @GetMapping
    public ResponseEntity<SavingStatusResponse> getSaveStatus(
            @RequestParam long announcementId,
            @RequestParam long userId) {
        return ResponseEntity.ok(savingService.getSavingStatus(announcementId, userId));
    }
}
