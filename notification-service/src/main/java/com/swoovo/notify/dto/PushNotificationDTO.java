package com.swoovo.notify.dto;

public record PushNotificationDTO(String deviceToken,
                                  String title,
                                  String message) implements NotificationDTO {
}
