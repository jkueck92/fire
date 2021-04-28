package de.jkueck.fire.component;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.database.Alert;
import de.jkueck.fire.service.alert.AlertService;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class DatabaseComponent extends AlertBaseComponent {

    private final AlertService alertService;

    public DatabaseComponent(AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    void onReceiveAlert(AlertMessage alertMessage) {

        Alert alert = new Alert();
        alert.setTimestamp(alertMessage.getTimestamp());
        alert.setCompleteMessage(alertMessage.getCompleteMessage());

        Optional<Alert> dbAlert = this.alertService.save(alert);

        log.info("store alert in database (" + dbAlert + ")");

    }

    @Override
    boolean isComponentEnabled() {
        return this.getSystemSettingService().getAsBoolean(SystemSettings.SYSTEM_SETTINGS_IS_DATABASE_ENABLED);
    }

}
