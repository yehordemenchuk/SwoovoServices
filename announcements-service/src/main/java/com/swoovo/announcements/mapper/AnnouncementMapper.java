package com.swoovo.announcements.mapper;

import com.swoovo.announcements.dto.request.AnnouncementRequest;
import com.swoovo.announcements.dto.response.AnnouncementResponse;
import com.swoovo.announcements.entity.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.swoovo.support.util.MinioUtil;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {
    @Mapping(target = "imageFilePath", expression = "java(mapImage(announcementRequest))")
    Announcement fromRequest(AnnouncementRequest announcementRequest);

    AnnouncementResponse toResponse(Announcement announcement);

    default String mapImage(AnnouncementRequest announcementRequest) {
        return MinioUtil.getFileName(announcementRequest.image());
    }
}
