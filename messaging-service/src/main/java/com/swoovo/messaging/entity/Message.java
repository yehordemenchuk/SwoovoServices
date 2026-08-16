package com.swoovo.messaging.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_sequence", allocationSize = 1)
    private Long id;

    private Long senderId;

    private String content;

    private String messageType = "TEXT";

    private String timestamp;

    private boolean delivered = false;

    private Instant createdAt = Instant.now();

    @Nullable
    @ElementCollection
    private List<String> attachmentsFilePaths;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
