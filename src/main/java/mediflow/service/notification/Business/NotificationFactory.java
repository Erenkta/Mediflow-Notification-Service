package mediflow.service.notification.Business;

import mediflow.service.notification.Business.Strategy.NotificationStrategy;
import mediflow.service.notification.Domain.Enum.NotificationType;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationFactory {
    private final Map<NotificationType, NotificationStrategy> strategies;

    public NotificationFactory(List<NotificationStrategy> strategies) {
        this.strategies = strategies.stream().collect(
                Collectors.toMap(NotificationStrategy::getSupportedType,s->s));
    }

    public NotificationStrategy getStrategy(NotificationType type) {
        NotificationStrategy strategy = strategies.get(type);
        if(strategy == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
        return strategy;
    }

}
