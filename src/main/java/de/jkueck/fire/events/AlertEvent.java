package de.jkueck.fire.events;

import de.jkueck.fire.AlertMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AlertEvent extends ApplicationEvent {

    @Getter
    private final AlertMessage alertMessage;

    public AlertEvent(Object source, AlertMessage alertMessage) {
        super(source);
        this.alertMessage = alertMessage;
    }

}
