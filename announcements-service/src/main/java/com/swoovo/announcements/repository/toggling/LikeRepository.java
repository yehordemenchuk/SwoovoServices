package com.swoovo.announcements.repository.toggling;

import com.swoovo.announcements.entity.toggling.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>, TogglingEntityRepository<Like> {
    Optional<Like> findByAnnouncementIdAndUserId(long announcementId, long userId);

    long countByAnnouncementId(long announcementId);

    boolean existsByAnnouncementIdAndUserId(long announcementId, long userId);
}
