package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.SettingNames;
import de.jkueck.fire.database.entity.Setting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class DiveraComponent extends BaseComponent {

    @Override
    void execute(AlertMessage alertMessage) {
        String accessToken = this.getSettingService().getStringValue(SettingNames.SETTING_DIVERA_ACCESS_TOKEN);
        if (StringUtils.isNotBlank(accessToken)) {
            log.info("send message to divera (this is divera message) with access key (" + accessToken + ")");
        }
    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        if (this.getSettingService().getBooleanValue(SettingNames.SETTING_IS_DIVERA_ENABLED)) {
            this.execute(alertEvent.getAlertMessage());
        }
    }
}
