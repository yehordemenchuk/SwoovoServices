package com.swoovo.complaints.mapper;

import com.swoovo.complaints.dto.ComplaintRequest;
import com.swoovo.complaints.dto.ComplaintResponse;
import com.swoovo.complaints.entity.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.swoovo.support.util.MinioUtil;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    @Mapping(target = "imageFilePath", expression = "java(mapImage(complaintRequest))")
    Complaint fromRequest(ComplaintRequest complaintRequest);

    ComplaintResponse toResponse(Complaint complaint);

    default String mapImage(ComplaintRequest complaintRequest) {
        return MinioUtil.getFileName(complaintRequest.image());
    }
}
