package com.swoovo.posts.mapper;

import com.swoovo.posts.dto.PostRequest;
import com.swoovo.posts.dto.PostResponse;
import com.swoovo.posts.entities.Post;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import org.swoovo.support.util.MinioUtil;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "mediaFileNames", expression = "java(mapMedia(postRequest, minioUtil))")
    Post fromRequest(PostRequest postRequest, @Context MinioUtil minioService);

    @Mapping(target = "mediaUrls", expression = "java(mapMediaUrls(post, minioUtil))")
    PostResponse toResponse(Post post, @Context MinioUtil minioUtil);

    default List<String> mapMedia(PostRequest postRequest,
                                  MinioUtil minioService) {
        try {
            List<MultipartFile> mediaFiles = postRequest.media();

            uploadMediaFiles(mediaFiles, minioService);

            return mediaFiles
                    .stream()
                    .map(MultipartFile::getName)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default List<String> mapMediaUrls(Post post, MinioUtil minioService) {
        return post.getMediaFileNames()
                .stream().map(minioService::downloadFile)
                .toList();
    }

    default void uploadMediaFiles(List<MultipartFile> mediaFiles,
                                  MinioUtil minioService) throws IOException {
        for (MultipartFile media : mediaFiles) {
            minioService.uploadFile(media.getName(),
                    media.getInputStream(),
                    media.getSize(),
                    media.getContentType());
        }
    }
}
