package com.swoovo.announcements.repository;

import com.swoovo.announcements.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    Optional<Saving> findByAnnouncementIdAndUserId(long announcementId, long userId);

    long countByAnnouncementId(long announcementId);

    boolean existsByAnnouncementIdAndUserId(long announcementId, long userId);
}
