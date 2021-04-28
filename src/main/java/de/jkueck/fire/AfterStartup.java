package de.jkueck.fire;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.service.setting.SystemSettingService;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AfterStartup {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SystemSettingService systemSettingService;


    public AfterStartup(ApplicationEventPublisher applicationEventPublisher, SystemSettingService systemSettingService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.systemSettingService = systemSettingService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void read() {
        log.info("ready, start checking settings");
        for (SystemSettings systemSetting : SystemSettings.values()) {
            log.info("setting (" + systemSetting + ") is in code available");
            Optional<SystemSetting> optionalSystemSetting = this.systemSettingService.findByName(systemSetting.getValue());
            if (optionalSystemSetting.isPresent()) {
                log.info("found setting (" + systemSetting + ") in database");
            } else {
                log.info("setting (" + systemSetting + ") not in database, create new setting");
                Optional<SystemSetting> optionalNewDbSetting = this.systemSettingService.save(systemSetting.getValue(), systemSetting.getDefaultValue());
                if (optionalNewDbSetting.isPresent()) {
                    log.info("store new setting (" + systemSetting + "), with default value (" + systemSetting.getDefaultValue() + ")");
                }
            }
        }
    }

}
