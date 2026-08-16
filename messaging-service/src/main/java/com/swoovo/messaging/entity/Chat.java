package com.swoovo.messaging.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Chat {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_sequence", allocationSize = 1)
    private Long id;

    private long firstUserId;

    private long secondUserId;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> messages;
}
