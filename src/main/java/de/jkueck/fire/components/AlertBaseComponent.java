package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.service.SystemSettingService;
import org.springframework.context.ApplicationListener;

public abstract class AlertBaseComponent extends BaseComponent implements ApplicationListener<AlertEvent> {

    protected AlertBaseComponent(SystemSettingService systemSettingService) {
        super(systemSettingService);
    }

    abstract void execute(AlertMessage alertMessage);

    abstract boolean isEnabled();

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        if (this.isEnabled()) {
            this.execute(alertEvent.getAlertMessage());
        }
    }

}
