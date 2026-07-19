package com.swoovo.notify.dto;

public record SmsNotificationDTO(String to,
                                 String message) implements NotificationDTO {
}
