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
            (event)-> "Get Well Soon"),

    APPOINTMENT_APPROVED("""
            Dear Patient,
            Your appointment has been approved. Appointment Details:
            """,
            (event)-> String.format(
            """
                    Approved Doctor Name : %s
                    Department Name : %s
                    Appointment Date : %s
                    """,event.data().get("doctorName"),event.data().get("departmentName"),event.data().get("date"))),

    APPOINTMENT_SOON("""
            Dear Patient,
            This is a Appointment Reminder. Please be ready before the appointment date. Appointment Details:
            """,
            (event)-> String.format(
                    """
                            Approved Doctor Name : %s
                            Department Name : %s
                            Appointment Date : %s
                            """,event.data().get("doctorName"),event.data().get("departmentName"),event.data().get("date"))),
    APPOINTMENT_DONE("""
            Dear Patient,
            Your appointment is Done. Please check your medical records.
            """,
            (event)-> "Get Well Soon"),

    BILLING_DEPOSIT_CREATED("""
            Dear Patient,
            Deposit payment of your appointment has been created. Please make the payment until the payment date. Deposit Details:
            """,
            (event)-> String.format(
                    """
                            Billing Date : %s
                            Payment Date : %s
                            Appointment Date : %s,
                            Amount: %s
                    """,
                    event.data().get("billingDate"),
                    event.data().get("paymentDate"),
                    event.data().get("appointmentDate"),
                    event.data().get("amount"))),
    BILLING_TREATMENT_CREATED("""
            Dear Patient,
            Treatment payment of your appointment has been created. Treatment Payment Details:
            """,
            (event)-> String.format(
                    """
                            Billing Date : %s
                            Payment Date : %s
                            Appointment Date : %s,
                            Amount: %s
                    """,
                    event.data().get("billingDate"),
                    event.data().get("paymentDate"),
                    event.data().get("appointmentDate"),
                    event.data().get("amount"))),
    BILLING_STATUS_UPDATED("""
            Dear Patient,
            
            Your billing status has been updated. Billing Details : 
            """,       (event)-> String.format(
            """
                    Billing Date : %s,
                    Billing Status (OLD) :%s,
                    Billing Status (NEW) :%s,
                    Payment Date : %s
                    Appointment Date : %s,
                    Amount: %s
            """,
            event.data().get("billingDate"),
            event.data().get("oldStatus"),
            event.data().get("newStatus"),
            event.data().get("paymentDate"),
            event.data().get("appointmentDate"),
            event.data().get("amount"))),

    ;

    public String message;
    public Function<NotificationEvent,String> messageFunction;

    EventMessage(String message, Function<NotificationEvent,String> messageFunction){
        this.message = message;
        this.messageFunction = messageFunction;
    }

}
