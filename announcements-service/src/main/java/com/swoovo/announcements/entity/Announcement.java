package com.swoovo.announcements.entity;

import com.swoovo.announcements.entity.toggling.Like;
import com.swoovo.announcements.entity.toggling.Saving;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_seq")
    @SequenceGenerator(name = "announcement_seq", sequenceName = "announcement_sequence",
            allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private String imageFilePath;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private Long userId;

    @OneToMany(mappedBy = "announcement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "announcement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "announcement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Saving> savings = new ArrayList<>();
}
