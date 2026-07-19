package com.swoovo.announcements.mapper;

import com.swoovo.announcements.dto.request.CommentRequest;
import com.swoovo.announcements.dto.response.CommentResponse;
import com.swoovo.announcements.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment fromRequest(CommentRequest commentRequest);

    CommentResponse toResponse(Comment comment);
}
