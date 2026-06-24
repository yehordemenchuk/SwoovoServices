package com.swoovo.friends.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class FriendsRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_record_seq")
    @SequenceGenerator(name = "friend_record_seq",
            sequenceName = "friend_record_sequence", allocationSize = 1)
    private Long id;

    private Long firstUserId;

    private Long secondUserId;
}
