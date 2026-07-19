package com.swoovo.notify.service;

import com.swoovo.notify.dto.NotificationDTO;

public interface NotificationSenderService<T extends NotificationDTO> {
    void sendNotification(T notificationDTO);
}
