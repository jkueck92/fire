package de.jkueck.fire;

import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class SerialPortComponent {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SerialPortComponent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    private void publishEvent() {
        log.info("publish event");

        AlertMessage alertMessage = new AlertMessage.Builder("Ritterhude Bremer Landstraße 40/ / H/052/Türnotöffnung/Hinterhaus/ Hilflos im Haus/", LocalDateTime.now()).build();

        this.applicationEventPublisher.publishEvent(new AlertEvent(this, alertMessage));
    }

    public void x() {
        SerialPort serialPort = SerialPort.getCommPort("COM1");
        log.info(serialPort.getSystemPortName());
    }

    @PostConstruct
    public void init() {
        // this.publishEvent();
        this.x();
    }

}
