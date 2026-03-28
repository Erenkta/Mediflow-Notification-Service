package mediflow.service.notification.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediflow.service.notification.Domain.Dto.NotificationEvent;
import mediflow.service.notification.Domain.Entity.NotificationHistory;
import mediflow.service.notification.Domain.Enum.EventMessage;
import mediflow.service.notification.Domain.Enum.NotificationStatus;
import mediflow.service.notification.Repository.NotificationHistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationHistoryRepository repository;

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(NotificationEvent event){
        log.info("RabbitMQ Message received from 'notification.queue' : "+event.eventType());
        String eventMessage = getMessage(event.eventType(),event);
        NotificationHistory history = new NotificationHistory().toBuilder()
                .userId(event.receiverId())
                .message(eventMessage)
                .eventType(event.eventType())
                .status(NotificationStatus.PENDING)
                .sentAt(LocalDateTime.now())
                .build();
        repository.save(history);

    }
    private String getMessage(String event, NotificationEvent body){
        EventMessage selectedEvent = Arrays.stream(EventMessage.values()).filter(item -> item.name().equals(event)).findFirst().orElse(EventMessage.DEFAULT);
        return  String.join("\n",selectedEvent.message, selectedEvent.messageFunction.apply(body));
    }
}
