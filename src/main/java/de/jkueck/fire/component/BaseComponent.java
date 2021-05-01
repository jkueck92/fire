package de.jkueck.fire.component;

import de.jkueck.fire.service.setting.SystemSettingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseComponent {

    @Getter
    @Autowired
    private SystemSettingService systemSettingService;

}
