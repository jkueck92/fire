package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DiveraComponent extends BaseComponent {

    protected DiveraComponent(SystemSettingService systemSettingService) {
        super(systemSettingService);
    }

    @Override
    void execute(AlertMessage alertMessage) {

        String diveraMessage = this.getSystemSettingService().getDiveraMessage();
        String diveraAccessKey = this.getSystemSettingService().getDiveraAccessKey();

        log.info("send message to divera (" + diveraMessage + ") with access key (" + diveraAccessKey + ")");

    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        if (this.getSystemSettingService().isDiveraEnabled()) {
            this.execute(alertEvent.getAlertMessage());
        }
    }
}
