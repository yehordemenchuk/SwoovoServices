package com.swoovo.announcements.service.toggling;

import com.swoovo.announcements.dto.request.toggling.LikeRequest;
import com.swoovo.announcements.dto.response.LikeStatusResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.toggling.Like;
import com.swoovo.announcements.mapper.toggling.LikeMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.toggling.LikeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService extends TogglingEntityService<Like, LikeRequest, LikeStatusResponse> {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository,
                       LikeMapper likeMapper,
                       AnnouncementRepository announcementRepository) {
        super(likeRepository, likeMapper, announcementRepository);
        this.likeRepository = likeRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="like_status", allEntries = true)
    })
    public LikeStatusResponse toggleLike(LikeRequest likeRequest) {
        Announcement announcement = findAnnouncement(likeRequest);

        return addEntity(likeRequest, () -> formLikeStatus(true, announcement.getId()),
                () -> formLikeStatus(false, announcement.getId()),
                announcement.getLikes(),
                announcement);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "like_status", key = "#announcementId")
    public LikeStatusResponse getLikeStatus(long announcementId, long userId) {
        return formLikeStatus(likeRepository
                        .existsByAnnouncementIdAndUserId(announcementId, userId),
                announcementId);
    }

    private LikeStatusResponse formLikeStatus(boolean liked, long announcementId) {
        return new LikeStatusResponse(liked, likeRepository
                .countByAnnouncementId(announcementId));
    }
}
