package mediflow.service.notification.Domain.Enum;

import mediflow.service.notification.Domain.Dto.NotificationEvent;
import java.util.function.Function;

public enum EventMessage {
    APPOINTMENT_CREATED("""
            Dear Patient,
            Your appointment has been created and waiting for approval. Appointment Details:
            """,
            (event)-> String.format(
                    """
                    Doctor Name : %s
                    Department Name : %s
                    Appointment Date : %s
                    """,event.data().get("doctorName"),event.data().get("departmentName"),event.data().get("date"))),
    DEFAULT("""
            Dear Patient,
            Please check your appointment details from the application.
            """,
            (event)-> "Get Well Soon")


    ;

    public String message;
    public Function<NotificationEvent,String> messageFunction;

    EventMessage(String message, Function<NotificationEvent,String> messageFunction){
        this.message = message;
        this.messageFunction = messageFunction;
    }

}
