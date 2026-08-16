package com.swoovo.messaging.service;

import com.swoovo.messaging.dto.request.MessageRequest;
import com.swoovo.messaging.dto.response.MessageResponse;
import com.swoovo.messaging.entity.Chat;
import com.swoovo.messaging.entity.Message;
import com.swoovo.messaging.mapper.MessageMapper;
import com.swoovo.messaging.repository.ChatRepository;
import com.swoovo.messaging.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swoovo.support.util.MinioUtil;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final MinioUtil minioUtil;

    @Transactional
    public MessageResponse saveMessage(MessageRequest messageRequest) {
        Message message = messageMapper.fromMessageRequest(messageRequest);

        if (Objects.nonNull(messageRequest.chatId())) {
            Chat chat = chatRepository.findById(messageRequest.chatId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Chat not found with id: "
                                    + messageRequest.chatId()));

            message.setChat(chat);
        }

        return getMessageResponse(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> findAllFromChat(Pageable pageable, long chatId) {
        return messageRepository.findByChatId(chatId, pageable)
                .map(this::getMessageResponse);
    }

    @Transactional(readOnly = true)
    public MessageResponse viewMessage(long id) throws EntityNotFoundException {
        Message message = messageRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return getMessageResponse(message);
    }

    @Transactional
    public void deleteMessage(long id) {
        if (!messageRepository.existsById(id)) {
            throw new EntityNotFoundException("Message not found with id: " + id);
        }

        messageRepository.deleteById(id);
    }

    private MessageResponse getMessageResponse(Message message) {
        MessageResponse messageResponse = messageMapper.toMessageResponse(message);

        List<String> attachmentsFilePaths = message.getAttachmentsFilePaths();

        if (Objects.nonNull(attachmentsFilePaths))
            attachmentsFilePaths
                    .forEach(path -> messageResponse
                            .attachmentsUrls()
                            .add(minioUtil.downloadFile(path)));

        return messageResponse;
    }
}
