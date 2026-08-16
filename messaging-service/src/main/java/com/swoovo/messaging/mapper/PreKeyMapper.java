package com.swoovo.messaging.mapper;

import com.swoovo.messaging.dto.request.KeyBundleRequest;
import com.swoovo.messaging.dto.response.PreKeyResponse;
import com.swoovo.messaging.entity.PreKey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PreKeyMapper {
    PreKey fromBundle(KeyBundleRequest bundleRequest);

    PreKeyResponse toPreKeyResponse(PreKey preKey);
}
