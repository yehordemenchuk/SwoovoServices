package com.swoovo.announcements.repository.toggling;

import com.swoovo.announcements.entity.toggling.TogglingEntity;

import java.util.Optional;

public interface TogglingEntityRepository<T extends TogglingEntity> {
    Optional<T> findByAnnouncementIdAndUserId(long announcementId, long userId);

    void delete(T entity);

    T save(T entity);
}
