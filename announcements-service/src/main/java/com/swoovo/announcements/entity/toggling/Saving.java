package com.swoovo.announcements.entity.toggling;

import com.swoovo.announcements.entity.Announcement;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@Data
public class Saving implements TogglingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "save_seq")
    @SequenceGenerator(name = "save_seq", sequenceName = "save_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @NonNull
    private Long userId;
}
