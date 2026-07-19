package com.swoovo.notify.controller;

import com.swoovo.notify.dto.EmailNotificationDTO;
import com.swoovo.notify.dto.PushNotificationDTO;
import com.swoovo.notify.dto.SmsNotificationDTO;
import com.swoovo.notify.service.email.EmailSenderService;
import com.swoovo.notify.service.push.PushNotificationService;
import com.swoovo.notify.service.sms.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notify/")
@RequiredArgsConstructor
public class NotificationController {
    private final PushNotificationService pushNotificationService;
    private final EmailSenderService emailSenderService;
    private final SmsNotificationService smsNotificationService;

    @PostMapping("push")
    public ResponseEntity<String> sendPushNotification(@RequestBody PushNotificationDTO notificationDTO) {
        pushNotificationService.sendNotification(notificationDTO);

        return ResponseEntity.ok("Push Notification sent");
    }

    @PostMapping("email")
    public ResponseEntity<String> sendEmailNotification(@RequestBody EmailNotificationDTO notificationDTO) {
        emailSenderService.sendNotification(notificationDTO);

        return ResponseEntity.ok("Email NotificationDTO sent");
    }


    @PostMapping("sms")
    public ResponseEntity<String> sendSmsNotification(@RequestBody SmsNotificationDTO notificationDTO) {
        smsNotificationService.sendNotification(notificationDTO);

        return ResponseEntity.ok("Sms NotificationDTO sent");
    }
}
