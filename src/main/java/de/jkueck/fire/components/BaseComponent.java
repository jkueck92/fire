package de.jkueck.fire.components;

import de.jkueck.fire.service.SystemSettingService;
import lombok.Getter;

public abstract class BaseComponent {

    @Getter
    private final SystemSettingService systemSettingService;

    protected BaseComponent(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

}
