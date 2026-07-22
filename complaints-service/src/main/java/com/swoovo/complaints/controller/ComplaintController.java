package com.swoovo.complaints.controller;

import com.swoovo.complaints.dto.ComplaintRequest;
import com.swoovo.complaints.dto.ComplaintResponse;
import com.swoovo.complaints.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/complaints/")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ComplaintResponse> createComplaint(@Valid @ModelAttribute
                                                             ComplaintRequest complaintRequest) {
        ComplaintResponse complaintResponse = complaintService
                .createComplaint(complaintRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(complaintResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(complaintResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ComplaintResponse> findComplaintById(@PathVariable long id) {
        return ResponseEntity.ok(complaintService.findComplaintById(id));
    }


    @GetMapping("user-id/{userId}")
    public ResponseEntity<List<ComplaintResponse>> findComplaintsByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(complaintService.findComplaintsByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> findAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable long id) {
        complaintService.deleteComplaintById(id);

        return ResponseEntity.noContent().build();
    }
}
