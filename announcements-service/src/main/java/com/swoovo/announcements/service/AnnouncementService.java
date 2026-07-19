package com.swoovo.announcements.service;

import com.swoovo.announcements.dto.request.AnnouncementRequest;
import com.swoovo.announcements.dto.response.AnnouncementResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.mapper.AnnouncementMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swoovo.support.util.MinioUtil;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final MinioUtil minioUtil;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "announcement", allEntries = true),
            @CacheEvict(value = "announcements", allEntries = true),
    })
    public AnnouncementResponse createAnnouncement(AnnouncementRequest announcementRequest) {
        Announcement announcement = announcementMapper.fromRequest(announcementRequest);

        minioUtil.uploadFile(announcementRequest.image());

        announcementRepository.save(announcement);

        return announcementMapper.toResponse(announcement);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "announcement", key = "#id")
    public AnnouncementResponse findAnnouncementById(long id) throws EntityNotFoundException {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return announcementMapper.toResponse(announcement);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "announcements")
    public Page<AnnouncementResponse> findAllAnnouncements(Pageable pageable) {
        return announcementRepository.findAll(pageable)
                .map(announcementMapper::toResponse);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "announcement", allEntries = true),
            @CacheEvict(value = "announcements", allEntries = true),
    })
    public void deleteById(long id) throws EntityNotFoundException {
        if (!announcementRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        announcementRepository.deleteById(id);
    }
}
