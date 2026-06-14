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
import org.springframework.web.multipart.MultipartFile;
import org.swoovo.support.util.MinioUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

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
        Post post = postJpaRepository.save(postMapper.fromRequest(postRequest));

        uploadMediaFiles(postRequest.media());

        return getPostResponse(post);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", value = "post")
    public PostResponse findById(long id) {
        Post post = postJpaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Post not found with id: " + id));

        return getPostResponse(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable)
                .map(this::getPostResponse);
    }

    @Cacheable(key = "#userId", value = "posts")
    public Page<PostResponse> findByUserId(long userId, Pageable pageable) {
        return postJpaRepository.findPostsByUserId(userId, pageable)
                .map(this::getPostResponse);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "post", allEntries = true)
    })
    public void deletePostById(long id) {
        if (!postJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }

        postJpaRepository.deleteById(id);
    }

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = postMapper.toResponse(post);

        postResponse.setMediaUrls(downloadMediaUrls(post.getMediaFileNames()));

        return postResponse;
    }

    private void uploadMediaFiles(List<MultipartFile> mediaFiles) throws UncheckedIOException {
        try {
            for (MultipartFile media : mediaFiles) {
                minioUtil.uploadFile(media.getName(),
                        media.getInputStream(),
                        media.getSize(),
                        media.getContentType());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<String> downloadMediaUrls(List<String> mediaFileNames) {
        return mediaFileNames.stream()
                .map(minioUtil::downloadFile)
                .toList();
    }
}
