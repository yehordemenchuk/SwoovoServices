package com.swoovo.announcements.service;

import com.swoovo.announcements.dto.request.SavingRequest;
import com.swoovo.announcements.dto.response.SavingStatusResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.Saving;
import com.swoovo.announcements.mapper.SavingMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.SavingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final AnnouncementRepository announcementRepository;
    private final SavingRepository savingRepository;
    private final SavingMapper savingMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="saving_status", allEntries = true)
    })
    public SavingStatusResponse addSaving(SavingRequest savingRequest) {
        Announcement announcement = announcementRepository.findById(savingRequest.announcementId())
                .orElseThrow(EntityNotFoundException::new);

        Saving saving = savingMapper.fromRequest(savingRequest);

        announcement.getSavings().add(saving);

        saving.setAnnouncement(announcement);

        savingRepository.save(saving);

        announcementRepository.save(announcement);

        return new SavingStatusResponse(true);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "saving_status", key = "#announcementId")
    public SavingStatusResponse getSaveStatus(long announcementId, long userId) {
        return new SavingStatusResponse(savingRepository
                .existsByAnnouncementIdAndUserId(announcementId, userId));
    }
}
