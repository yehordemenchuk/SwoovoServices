package com.swoovo.complaints.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaint_seq")
    @SequenceGenerator(name = "complaint_seq", sequenceName = "complaint_sequence", allocationSize = 1)
    private Long id;

    @NonNull
    private Long userId;

    @Column(nullable = false, length = 2000)
    private String text;

    @NonNull
    private LocalDateTime createdAt;

    @Nullable
    private String imageFilePath;
}
