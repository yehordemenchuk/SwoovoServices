package com.swoovo.notify.dto;

public record EmailNotificationDTO(String to,
                                   String subject,
                                   String text) implements NotificationDTO {
}
