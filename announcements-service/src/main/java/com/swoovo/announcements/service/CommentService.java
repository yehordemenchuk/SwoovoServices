package com.swoovo.announcements.service;

import com.swoovo.announcements.dto.request.CommentRequest;
import com.swoovo.announcements.dto.response.CommentResponse;
import com.swoovo.announcements.entity.Announcement;
import com.swoovo.announcements.entity.Comment;
import com.swoovo.announcements.mapper.CommentMapper;
import com.swoovo.announcements.repository.AnnouncementRepository;
import com.swoovo.announcements.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AnnouncementRepository announcementRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponse addComment(CommentRequest request) throws EntityNotFoundException {
        Announcement announcement = announcementRepository.findById(request.announcementId())
                .orElseThrow(EntityNotFoundException::new);
        Comment comment = commentMapper.fromRequest(request);

        System.out.println(comment.getCreatedAt());

        comment.setAnnouncement(announcement);

        announcement.getComments().add(comment);

        commentRepository.save(comment);

        announcementRepository.save(announcement);

        return commentMapper.toResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllComments(long announcementId) {
        return commentRepository.findByAnnouncementIdOrderByCreatedAtAsc(announcementId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }
}
