package com.swoovo.announcements.service.toggling;

import com.swoovo.announcements.dto.request.toggling.SavingRequest;
import com.swoovo.announcements.dto.response.SavingStatusResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.toggling.Saving;
import com.swoovo.announcements.mapper.toggling.SavingMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.toggling.SavingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavingService extends TogglingEntityService<Saving, SavingRequest, SavingStatusResponse> {
    private final SavingRepository savingRepository;

    public SavingService(SavingRepository savingRepository,
                         SavingMapper savingMapper,
                         AnnouncementRepository announcementRepository) {
        super(savingRepository, savingMapper, announcementRepository);

        this.savingRepository = savingRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="saving_status", allEntries = true)
    })
    public SavingStatusResponse toggleSaving(SavingRequest savingRequest) throws EntityNotFoundException {
        Announcement announcement = findAnnouncement(savingRequest);

        return addEntity(savingRequest, () -> new SavingStatusResponse(true),
                () -> new SavingStatusResponse(false), announcement.getSavings(),
                announcement);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "saving_status", key = "#announcementId")
    public SavingStatusResponse getSavingStatus(long announcementId, long userId) {
        return new SavingStatusResponse(savingRepository
                .existsByAnnouncementIdAndUserId(announcementId, userId));
    }
}
