package com.swoovo.notify.service.sms;

import com.swoovo.notify.dto.SmsNotificationDTO;
import com.swoovo.notify.service.NotificationSenderService;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SmsNotificationService implements NotificationSenderService<SmsNotificationDTO> {
    @Value("${twilio.phone.number}")
    private String senderPhoneNumber;

    @Override
    public void sendNotification(SmsNotificationDTO notificationDTO) {
        Message.creator(
                new PhoneNumber(notificationDTO.to()),
                new PhoneNumber(senderPhoneNumber),
                notificationDTO.message()
        ).create();
    }
}
