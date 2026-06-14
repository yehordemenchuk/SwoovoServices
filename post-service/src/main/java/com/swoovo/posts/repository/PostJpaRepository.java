package com.swoovo.posts.repository;

import com.swoovo.posts.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostsByUserId(Long userId, Pageable pageable);
}
