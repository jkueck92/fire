package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.database.Alert;
import de.jkueck.fire.service.AlertService;
import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlertDatabaseComponent extends BaseComponent {

    private final AlertService alertService;

    protected AlertDatabaseComponent(SystemSettingService systemSettingService, AlertService alertService) {
        super(systemSettingService);
        this.alertService = alertService;
    }

    @Override
    void execute(AlertMessage alertMessage) {

        Alert alert = new Alert();
        alert.setTimestamp(alertMessage.getTimestamp());
        alert.setCompleteMessage(alertMessage.getCompleteMessage());

        this.alertService.save(alert);
        log.info("store new alert in database (" + alert + ")");
    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        if (this.getSystemSettingService().isDatabasePersistEnabled()) {
            this.execute(alertEvent.getAlertMessage());
        }
    }
}
