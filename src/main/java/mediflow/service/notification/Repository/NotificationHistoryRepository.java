package mediflow.service.notification.Repository;

import mediflow.service.notification.Domain.Entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory,Long> {
}
