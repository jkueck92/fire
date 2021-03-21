package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.service.SystemSettingService;
import lombok.Getter;
import org.springframework.context.ApplicationListener;

public abstract class BaseComponent implements ApplicationListener<AlertEvent> {

    @Getter
    private final SystemSettingService systemSettingService;

    protected BaseComponent(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    abstract void execute(AlertMessage alertMessage);

}
