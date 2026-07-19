package com.swoovo.announcements.service;

import com.swoovo.announcements.dto.request.LikeRequest;
import com.swoovo.announcements.dto.response.LikeStatusResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.Like;
import com.swoovo.announcements.mapper.LikeMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.LikeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final AnnouncementRepository announcementRepository;
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="like_status", allEntries = true)
    })
    public LikeStatusResponse addLike(LikeRequest likeRequest) throws EntityNotFoundException {
        Like like = likeMapper.fromRequest(likeRequest);

        long announcementId = likeRequest.announcementId();

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(EntityNotFoundException::new);

        announcement.getLikes().add(like);

        like.setAnnouncement(announcement);

        announcementRepository.save(announcement);

        likeRepository.save(like);

        return new LikeStatusResponse(true, likeRepository
                .countByAnnouncementId(announcementId));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "like_status", key = "#announcementId")
    public LikeStatusResponse getLikeStatus(long announcementId, long userId) {
        boolean liked = likeRepository.existsByAnnouncementIdAndUserId(announcementId, userId);

        long count = likeRepository.countByAnnouncementId(announcementId);

        return new LikeStatusResponse(liked, count);
    }
}
