package com.swoovo.posts.mapper;

import com.swoovo.posts.dto.PostRequest;
import com.swoovo.posts.dto.PostResponse;
import com.swoovo.posts.entities.Post;
import com.swoovo.posts.service.MinioService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-13T15:02:30+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post fromRequest(PostRequest postRequest, MinioService minioService) {
        if ( postRequest == null ) {
            return null;
        }

        Post post = new Post();

        post.setPostText( postRequest.postText() );
        post.setUserId( postRequest.userId() );
        post.setName( postRequest.name() );

        post.setMediaFileNames( mapMedia(postRequest, minioService) );

        return post;
    }

    @Override
    public PostResponse toResponse(Post post, MinioService minioService) {
        if ( post == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String postText = null;
        List<String> mediaFileNames = null;
        Long userId = null;

        id = post.getId();
        name = post.getName();
        postText = post.getPostText();
        List<String> list = post.getMediaFileNames();
        if ( list != null ) {
            mediaFileNames = new ArrayList<String>( list );
        }
        userId = post.getUserId();

        List<String> mediaUrls = mapMediaUrls(post, minioService);

        PostResponse postResponse = new PostResponse( id, name, postText, mediaFileNames, userId, mediaUrls );

        return postResponse;
    }
}
