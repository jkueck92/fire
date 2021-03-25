package de.jkueck.fire.components;

import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DiveraComponent extends BaseComponent {

    @Override
    void execute(AlertMessage alertMessage) {

    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        // TODO
    }
}
