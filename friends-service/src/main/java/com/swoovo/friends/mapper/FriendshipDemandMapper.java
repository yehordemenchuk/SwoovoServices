package com.swoovo.friends.mapper;

import com.swoovo.friends.dto.request.FriendshipDemandRequest;
import com.swoovo.friends.dto.response.FriendshipDemandResponse;
import com.swoovo.friends.entities.FriendshipDemandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendshipDemandMapper {
    FriendshipDemandEntity fromRequest(FriendshipDemandRequest friendshipDemandRequest) ;

    FriendshipDemandResponse toResponse(FriendshipDemandEntity friendshipDemandEntity);
}
