package com.swoovo.complaints.mapper;

import com.swoovo.complaints.dto.ComplaintRequest;
import com.swoovo.complaints.dto.ComplaintResponse;
import com.swoovo.complaints.entity.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.swoovo.support.util.MinioUtil;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    @Mapping(target = "imagesFilePaths", expression = "java(mapImages(complaintRequest))")
    Complaint fromRequest(ComplaintRequest complaintRequest);

    ComplaintResponse toResponse(Complaint complaint);

    default List<String> mapImages(ComplaintRequest complaintRequest) {
        if (Objects.isNull(complaintRequest.images()))
            return null;

        return complaintRequest.images().stream()
                .map(MinioUtil::getFileName)
                .toList();
    }
}
