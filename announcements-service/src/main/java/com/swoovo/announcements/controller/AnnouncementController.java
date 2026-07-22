package com.swoovo.announcements.controller;

import com.swoovo.announcements.dto.request.AnnouncementRequest;
import com.swoovo.announcements.dto.response.AnnouncementResponse;
import com.swoovo.announcements.service.AnnouncementService;
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
@RequestMapping("/api/v1/announcements/")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AnnouncementResponse> createAnnouncement(@Valid @ModelAttribute AnnouncementRequest announcementRequest) {
        AnnouncementResponse announcementResponse = announcementService
                .createAnnouncement(announcementRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(announcementResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(announcementResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> findAnnouncementById(@PathVariable long id) {
        AnnouncementResponse announcementResponse = announcementService.findAnnouncementById(id);
        return ResponseEntity.ok(announcementResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AnnouncementResponse>>> getAllAnnouncements(Pageable pageable,
                                                                                             PagedResourcesAssembler<AnnouncementResponse> assembler) {
        Page<AnnouncementResponse> page = announcementService.findAllAnnouncements(pageable);

        return ResponseEntity.ok(assembler.toModel(page));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        announcementService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
