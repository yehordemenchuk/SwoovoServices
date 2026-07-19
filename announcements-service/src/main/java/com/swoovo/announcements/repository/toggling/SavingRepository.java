package com.swoovo.announcements.repository.toggling;

import com.swoovo.announcements.entity.toggling.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer>, TogglingEntityRepository<Saving> {
    Optional<Saving> findByAnnouncementIdAndUserId(long announcementId, long userId);

    long countByAnnouncementId(long announcementId);

    boolean existsByAnnouncementIdAndUserId(long announcementId, long userId);
}
