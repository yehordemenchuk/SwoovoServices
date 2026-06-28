package com.swoovo.friends.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class FriendshipDemandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_request_seq")
    @SequenceGenerator(name = "friend_request_seq", sequenceName = "friend_request_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime createdAt;
}
