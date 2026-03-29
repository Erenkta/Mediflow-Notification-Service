package mediflow.service.notification.Business.Strategy;

import mediflow.service.notification.Domain.Dto.NotificationEvent;
import mediflow.service.notification.Domain.Enum.NotificationType;

public interface NotificationStrategy {
    void send(NotificationEvent event);
    NotificationType getSupportedType();
}
