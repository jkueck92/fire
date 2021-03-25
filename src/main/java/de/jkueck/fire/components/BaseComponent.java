package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import org.springframework.context.ApplicationListener;

public abstract class BaseComponent implements ApplicationListener<AlertEvent> {

    abstract void execute(AlertMessage alertMessage);

}
