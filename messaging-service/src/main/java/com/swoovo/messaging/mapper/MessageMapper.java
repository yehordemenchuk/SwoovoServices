package com.swoovo.messaging.mapper;

import com.swoovo.messaging.dto.request.MessageRequest;
import com.swoovo.messaging.dto.response.MessageResponse;
import com.swoovo.messaging.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.swoovo.support.util.MinioUtil;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "attachmentsUrlPaths",
            expression = "java(mapAttachments(messageRequest))")
    Message fromMessageRequest(MessageRequest messageRequest);

    MessageResponse toMessageResponse(Message message);

    default List<String> mapAttachments(MessageRequest messageRequest) {
        if (Objects.isNull(messageRequest.attachments()))
            return null;

        return messageRequest.attachments().stream()
                .map(MinioUtil::getFileName)
                .toList();
    }
}
