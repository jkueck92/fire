package de.jkueck.fire.components;

import de.jkueck.fire.service.SystemSettingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseComponent {

    @Getter
    @Autowired
    private SystemSettingService systemSettingService;

}
