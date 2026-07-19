package com.swoovo.notify.service.push;

import com.swoovo.notify.dto.PushNotificationDTO;
import com.swoovo.notify.service.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushNotificationService implements NotificationSenderService<PushNotificationDTO> {
    private final FirebaseService firebaseService;

    @Override
    public void sendNotification(PushNotificationDTO notificationDTO) {
        try {
            firebaseService.send(notificationDTO.deviceToken(),
                    notificationDTO.title(),
                    notificationDTO.message());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
