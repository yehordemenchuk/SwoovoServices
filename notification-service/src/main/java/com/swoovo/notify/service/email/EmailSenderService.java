package com.swoovo.notify.service.email;

import com.swoovo.notify.dto.EmailNotificationDTO;
import com.swoovo.notify.service.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements NotificationSenderService<EmailNotificationDTO> {
    private static final String EMAIL_ADDRESS = "demenchuk1210m@gmail.com";

    private final MailSender mailSender;

    @Override
    public void sendNotification(EmailNotificationDTO notificationDTO) {
        sendSimpleEmail(notificationDTO.to(), notificationDTO.subject(),
                notificationDTO.text());
    }

    private void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(EMAIL_ADDRESS);

        mailSender.send(message);
    }
}
