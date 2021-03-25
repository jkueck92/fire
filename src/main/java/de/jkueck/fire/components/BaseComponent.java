package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.service.ISettingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public abstract class BaseComponent implements ApplicationListener<AlertEvent> {

    @Getter
    @Autowired
    private ISettingService settingService;

    abstract void execute(AlertMessage alertMessage);

}
