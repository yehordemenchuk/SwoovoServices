package com.swoovo.friends.mapper;

import com.swoovo.friends.dto.request.FriendsRecordRequest;
import com.swoovo.friends.dto.response.FriendsRecordResponse;
import com.swoovo.friends.entities.FriendsRecordEntity;
import com.swoovo.friends.entities.FriendshipDemandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendsRecordMapper {
    FriendsRecordEntity toEntity(FriendsRecordRequest friendsRecordRequest);

    FriendsRecordResponse toResponse(FriendsRecordEntity friendsRecordEntity);

    @Mapping(target = "firstUserId", expression = "java(mapSenderId(friendshipDemandEntity))")
    @Mapping(target = "secondUserId", expression = "java(mapReceiverId(friendshipDemandEntity))")
    FriendsRecordEntity fromDemand(FriendshipDemandEntity friendshipDemandEntity);

    default Long mapSenderId(FriendshipDemandEntity friendshipDemandEntity) {
        return friendshipDemandEntity.getSenderId();
    }

    default Long mapReceiverId(FriendshipDemandEntity friendshipDemandEntity) {
        return friendshipDemandEntity.getReceiverId();
    }
}
