package com.swoovo.announcements.service.toggling;

import com.swoovo.announcements.dto.request.toggling.TogglingEntityRequest;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.toggling.TogglingEntity;
import com.swoovo.announcements.mapper.toggling.TogglingEntityMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.toggling.TogglingEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class TogglingEntityService<T extends TogglingEntity, U extends TogglingEntityRequest, R> {
    private final TogglingEntityRepository<T> togglingEntityRepository;
    private final TogglingEntityMapper<T, U> togglingEntityMapper;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    protected R addEntity(U request, Supplier<R> positive,
                          Supplier<R> negative,
                          List<T> entities,
                          Announcement announcement) throws EntityNotFoundException {
        if (deleteIfSavingExisting(request))
            return negative.get();

        T entity = togglingEntityMapper.fromRequest(request);

        entities.add(entity);

        entity.setAnnouncement(announcement);

        announcementRepository.save(announcement);

        togglingEntityRepository.save(entity);

        return positive.get();
    }

    protected boolean deleteIfSavingExisting(U request) {
        Optional<T> existing = togglingEntityRepository.findByAnnouncementIdAndUserId(request
                .announcementId(), request.userId());

        if (existing.isPresent()) {
            togglingEntityRepository.delete(existing.get());

            return true;
        }

        return false;
    }

    protected Announcement findAnnouncement(U request) {
        return announcementRepository.findById(request.announcementId())
                .orElseThrow(EntityNotFoundException::new);
    }
}
