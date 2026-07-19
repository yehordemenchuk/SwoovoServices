package com.swoovo.announcements.mapper.toggling;

import com.swoovo.announcements.dto.request.toggling.LikeRequest;
import com.swoovo.announcements.entity.toggling.Like;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper extends TogglingEntityMapper<Like, LikeRequest> {
    Like fromRequest(LikeRequest likeRequest);
}
