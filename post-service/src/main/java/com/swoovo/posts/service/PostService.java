package com.swoovo.posts.service;

import com.swoovo.posts.dto.PostRequest;
import com.swoovo.posts.dto.PostResponse;
import com.swoovo.posts.entities.Post;
import com.swoovo.posts.mapper.PostMapper;
import com.swoovo.posts.repository.PostJpaRepository;
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
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final PostMapper postMapper;
    private final MinioUtil minioUtil;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "post", allEntries = true)
    })
    public PostResponse createPost(PostRequest postRequest) {
        Post post = postJpaRepository.save(postMapper.fromRequest(postRequest, minioUtil));

        return postMapper.toResponse(post, minioUtil);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", value = "post")
    public PostResponse findById(long id) {
        Post post = postJpaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Post not found with id: " + id));

        return postMapper.toResponse(post, minioUtil);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable)
                .map(post -> postMapper.toResponse(post, minioUtil));
    }

    @Cacheable(key = "#userId", value = "posts")
    public Page<PostResponse> findByUserId(long userId, Pageable pageable) {
        return postJpaRepository.findPostsByUserId(userId, pageable)
                .map(post -> postMapper.toResponse(post, minioUtil));
    }
}
