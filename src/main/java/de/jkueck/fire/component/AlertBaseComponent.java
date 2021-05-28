package de.jkueck.fire.component;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

@Slf4j
public abstract class AlertBaseComponent extends BaseComponent implements ApplicationListener<AlertEvent> {

    abstract void onReceiveAlert(AlertMessage alertMessage);

    abstract boolean isComponentEnabled();

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        log.info("check is component (" + this.getClass().getName() + ") enabled");
        if (this.isComponentEnabled()) {
            log.info("component (" + this.getClass().getName() + ") is enabled");
            this.onReceiveAlert(alertEvent.getAlertMessage());
        } else {
            log.info("component (" + this.getClass().getName() + ") is disabled");
        }
    }

}
