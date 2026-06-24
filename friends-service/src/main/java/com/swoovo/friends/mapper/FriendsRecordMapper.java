package com.swoovo.friends.mapper;

import com.swoovo.friends.dto.FriendsRecordRequest;
import com.swoovo.friends.dto.FriendsRecordResponse;
import com.swoovo.friends.entities.FriendsRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendsRecordMapper {
    FriendsRecordEntity toEntity(FriendsRecordRequest friendsRecordRequest);

    FriendsRecordResponse toResponse(FriendsRecordEntity friendsRecordEntity);
}
