package com.swoovo.announcements.mapper.toggling;

import com.swoovo.announcements.dto.request.toggling.SavingRequest;
import com.swoovo.announcements.entity.toggling.Saving;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavingMapper extends TogglingEntityMapper<Saving, SavingRequest>{
    Saving fromRequest(SavingRequest commentRequest);
}
