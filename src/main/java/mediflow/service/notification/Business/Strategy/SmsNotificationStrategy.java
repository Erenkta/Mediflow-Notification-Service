package mediflow.service.notification.Business.Strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediflow.service.notification.Domain.Dto.NotificationEvent;
import mediflow.service.notification.Domain.Enum.NotificationType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(NotificationEvent event) {
        log.info("{} event will be sent by {}", event.toString(), getSupportedType().name());
    }

    @Override
    public NotificationType getSupportedType() {
        return NotificationType.SMS;
    }
}
