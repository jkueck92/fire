package de.jkueck.fire.components;

import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemMonitoringComponent extends BaseComponent {

    protected SystemMonitoringComponent(SystemSettingService systemSettingService) {
        super(systemSettingService);
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void monitoring() {
        log.info("monitoring");
    }

}
