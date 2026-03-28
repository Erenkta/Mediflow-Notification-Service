package mediflow.service.notification.Domain.Dto;

import java.util.Map;

public record NotificationEvent(
        Long receiverId,
        String eventType, // make it enum later
        Map<String,String> data
) {
}