package mediflow.service.notification.Domain.Dto;

import mediflow.service.notification.Domain.Enum.NotificationType;

import java.util.Map;

public record NotificationEvent(
        Long receiverId,
        String eventType, // make it enum later
        NotificationType type,
        Map<String,String> data
) {
}