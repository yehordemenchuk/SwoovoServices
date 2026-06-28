package com.swoovo.friends.controller;

import com.swoovo.friends.dto.request.FriendshipDemandRequest;
import com.swoovo.friends.dto.response.FriendshipDemandResponse;
import com.swoovo.friends.entities.RequestStatus;
import com.swoovo.friends.service.FriendshipDemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demands/")
@RequiredArgsConstructor
public class FriendsDemandController {
    private final FriendshipDemandService friendshipDemandService;

    @PostMapping
    public ResponseEntity<FriendshipDemandResponse> createFriendshipDemand(@RequestBody FriendshipDemandRequest
                                                                                   friendshipDemandRequest) {
        FriendshipDemandResponse friendshipDemandResponse = friendshipDemandService.createFriendDemand(friendshipDemandRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(friendshipDemandResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(friendshipDemandResponse);
    }

    @GetMapping("{userId}")
    public List<FriendshipDemandResponse> getPendingDemands(@PathVariable long userId) {
        return friendshipDemandService.getPendingDemandsForUser(userId);
    }

    @PatchMapping
    public FriendshipDemandResponse changeDemandStatus(@RequestParam long id,
                                                       @RequestParam RequestStatus status) {
        return friendshipDemandService.changeDemandStatus(id, status);
    }
}
