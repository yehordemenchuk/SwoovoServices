package com.swoovo.friends.controller;

import com.swoovo.friends.dto.FriendsRecordRequest;
import com.swoovo.friends.dto.FriendsRecordResponse;
import com.swoovo.friends.service.FriendsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/friends/")
@RequiredArgsConstructor
public class FriendsController {
    private final FriendsService friendService;

    @PostMapping
    public ResponseEntity<FriendsRecordResponse> createFriendRecord(@Valid @RequestBody FriendsRecordRequest friendRequest) {
        FriendsRecordResponse response = friendService.createFriend(friendRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<FriendsRecordResponse> findFriendById(@PathVariable long id) {
        return ResponseEntity.ok(friendService.findById(id));
    }

    @GetMapping("by-id/{userId}")
    public ResponseEntity<List<Long>> findFriendsForUser(@PathVariable long userId) {
        return ResponseEntity.ok(friendService.findAllForUser(userId));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FriendsRecordResponse>>> findFriends(Pageable pageable,
                                                                                     PagedResourcesAssembler<FriendsRecordResponse> assembler) {
        Page<FriendsRecordResponse> friends = friendService.findAll(pageable);

        return ResponseEntity.ok(assembler.toModel(friends));
    }
}
