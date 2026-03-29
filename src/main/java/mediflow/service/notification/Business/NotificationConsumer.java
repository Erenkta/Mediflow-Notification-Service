package mediflow.service.notification.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediflow.service.notification.Audit.Service.AuditService;
import mediflow.service.notification.Business.Strategy.NotificationStrategy;
import mediflow.service.notification.Domain.Dto.NotificationEvent;
import mediflow.service.notification.Domain.Entity.NotificationHistory;
import mediflow.service.notification.Domain.Enum.EventMessage;
import mediflow.service.notification.Domain.Enum.NotificationStatus;
import mediflow.service.notification.Repository.NotificationHistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationHistoryRepository repository;
    private final NotificationFactory notificationFactory;
    private final AuditService auditService;

    @RabbitListener(queues = "notification.queue")
    @Transactional(transactionManager = "appTransactionManager")
    public void handleNotification(NotificationEvent event){
        log.info("RabbitMQ Message received from 'notification.queue' : "+event.eventType());
        String eventMessage = getMessage(event.eventType(),event);
        NotificationHistory history = new NotificationHistory().toBuilder()
                .userId(event.receiverId())
                .message(eventMessage)
                .eventType(event.eventType())
                .type(event.type())
                .status(NotificationStatus.PENDING)
                .sentAt(LocalDateTime.now())
                .build();
        repository.save(history);

        try{
            NotificationStrategy strategy = notificationFactory.getStrategy(event.type());
            strategy.send(event);
        }catch (Exception e){
            log.error("Notification sending failed!", e);
            history.setStatus(NotificationStatus.FAILED);
            auditService.log(event.eventType(),"Notification", history.getId(), "Notification-Service",e.getMessage());
        } finally {
            repository.save(history);
            auditService.log(event.eventType(),"Notification", history.getId(), "Notification-Service",history);
        }

    }
    private String getMessage(String event, NotificationEvent body){
        EventMessage selectedEvent = Arrays.stream(EventMessage.values()).filter(item -> item.name().equals(event)).findFirst().orElse(EventMessage.DEFAULT);
        return  String.join("\n",selectedEvent.message, selectedEvent.messageFunction.apply(body));
    }
}
