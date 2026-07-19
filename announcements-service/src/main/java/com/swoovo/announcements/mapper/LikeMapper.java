package com.swoovo.announcements.mapper;

import com.swoovo.announcements.dto.request.LikeRequest;
import com.swoovo.announcements.entity.Like;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    Like fromRequest(LikeRequest likeRequest);
}
