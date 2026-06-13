package com.swoovo.posts.mapper;

import com.swoovo.posts.dto.PostRequest;
import com.swoovo.posts.dto.PostResponse;
import com.swoovo.posts.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "mediaFileNames", expression = "java(mapMedia(postRequest, minioUtil))")
    Post fromRequest(PostRequest postRequest);

    PostResponse toResponse(Post post);

    default List<String> mapMedia(PostRequest postRequest) {
        List<MultipartFile> mediaFiles = postRequest.media();

        return mediaFiles
                .stream()
                .map(MultipartFile::getName)
                .toList();
    }
}
