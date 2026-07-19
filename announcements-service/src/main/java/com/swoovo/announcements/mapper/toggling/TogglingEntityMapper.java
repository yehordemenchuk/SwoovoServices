package com.swoovo.announcements.mapper.toggling;

import com.swoovo.announcements.dto.request.toggling.TogglingEntityRequest;
import com.swoovo.announcements.entity.toggling.TogglingEntity;

public interface TogglingEntityMapper<T extends TogglingEntity, U extends TogglingEntityRequest> {
    T fromRequest(U request);
}
