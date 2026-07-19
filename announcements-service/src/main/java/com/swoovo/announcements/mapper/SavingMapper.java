package com.swoovo.announcements.mapper;

import com.swoovo.announcements.dto.request.SavingRequest;
import com.swoovo.announcements.entity.Saving;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavingMapper {
    Saving fromRequest(SavingRequest commentRequest);
}
