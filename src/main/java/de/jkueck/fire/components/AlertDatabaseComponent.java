package de.jkueck.fire.components;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.Alert;
import de.jkueck.fire.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlertDatabaseComponent extends AlertBaseComponent {

    private final AlertService alertService;

    protected AlertDatabaseComponent(AlertService alertService) {
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
    boolean isEnabled() {
        return this.getSystemSettingService().getSystemSettingAsBoolean(SystemSettings.SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED);
    }

}
