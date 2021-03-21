package de.jkueck.fire;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AlertEvent extends ApplicationEvent {

    @Getter
    private AlertMessage alertMessage;

    public AlertEvent(Object source, AlertMessage alertMessage) {
        super(source);
        this.alertMessage = alertMessage;
    }

}
