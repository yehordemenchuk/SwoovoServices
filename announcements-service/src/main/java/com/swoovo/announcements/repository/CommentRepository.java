package com.swoovo.announcements.repository;

import com.swoovo.announcements.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAnnouncementIdOrderByCreatedAtAsc(long announcementId);

    long countByAnnouncementId(long announcementId);
}
