package mediflow.service.notification.Audit.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mediflow.service.notification.Audit.Entity.AuditLog;
import mediflow.service.notification.Audit.Repository.AuditLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditService {
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper mapper;

    @Transactional(transactionManager = "auditTransactionManager",propagation = Propagation.REQUIRES_NEW)
    public void log(String eventType, String entityName, Long entityId, String performedBy, Object details) {
        AuditLog logEntity = new AuditLog();
        logEntity.setEventType(eventType);
        logEntity.setEntityName(entityName.replace("ResponseDto",""));
        logEntity.setEntityId(entityId);
        logEntity.setPerformedBy(performedBy);
        try {
            logEntity.setDetails(details != null ? mapper.writeValueAsString(details) : null);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
            logEntity.setDetails("{}");
        }
        auditLogRepository.saveAndFlush(logEntity);
    }
}
