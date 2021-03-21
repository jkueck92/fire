package de.jkueck.fire.components;

import com.fazecast.jSerialComm.SerialPort;
import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class SerialReaderComponent {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SystemSettingService systemSettingService;

    public SerialReaderComponent(ApplicationEventPublisher applicationEventPublisher, SystemSettingService systemSettingService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.systemSettingService = systemSettingService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {

        log.info("start listening to (" + this.systemSettingService.getComPort() + ")");

        SerialPort serialPort = SerialPort.getCommPort(this.systemSettingService.getComPort());
        serialPort.openPort();

        try {

            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            InputStream in = serialPort.getInputStream();

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < 1000; ++j) {
                int c = in.read();

                log.info("receive character: (" + c + ") - (" + (char) c + ")");

                // 2 == Start of text
                // 3 == End of text
                // 13 == Carriage return
                // 10 == Line feed
                if (c != 2 && c != 3 && c != 13 && c != 10) {

                    if (c == 126) {
                        sb.append("ß");
                    } else if (c == 125) {
                        sb.append("ü");
                    } else if (c == 124) {
                        sb.append("ö");
                    } else if (c == 92) {
                        sb.append("Ö");
                    } else {
                        sb.append((char) c);
                    }

                }

                if (c == 3) {
                    log.info("stop reading");
                    log.info(sb.toString());



                    String timeAndDate = sb.substring(0, 14);
                    log.info("operation timestamp: (" + timeAndDate + ")");

                    String ric = sb.substring(14, 22);
                    log.info("ric: (" + ric + ")");

                    String message = sb.substring(22, sb.length());
                    log.info("message: (" + message + ")");

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yy");
                    Date timestamp = sdf.parse(timeAndDate);

                    String[] x = message.split("/");

                    for (String a : x) {
                        log.info("message: " + a);
                    }

                    AlertMessage.AlertMessageBuilder alertMessageBuilder = AlertMessage.builder();
                    alertMessageBuilder.completeMessage(message);
                    alertMessageBuilder.timestamp(timestamp);

                    if (ric != null) {
                        alertMessageBuilder.ric(ric);
                    }

                    String[] locationData = StringUtils.split(x[0], Character.toString((char)32), 2);

                    String city = StringUtils.trimToNull(locationData[0]);
                    if (city != null) {
                        alertMessageBuilder.city(city);
                    }

                    String street = StringUtils.trimToNull(locationData[1]);
                    if (street != null) {
                        alertMessageBuilder.street(street);
                    }

                    String object = StringUtils.trimToNull(x[1]);
                    if (object != null) {
                        alertMessageBuilder.object(object);
                    }

                    String category = StringUtils.trimToNull(x[2]);
                    if (category != null) {
                        alertMessageBuilder.category(category);
                    }

                    String keyword = StringUtils.trimToNull(x[3]);
                    if (keyword != null) {
                        alertMessageBuilder.keyword(keyword);
                    }

                    String keywordText = StringUtils.trimToNull(x[4]);
                    if (keywordText != null) {
                        alertMessageBuilder.keywordText(keywordText);
                    }

                    if (x.length >= 6) {
                        String remark1 = StringUtils.trimToNull(x[5]);
                        if (remark1 != null) {
                            alertMessageBuilder.remark1(remark1);
                        }
                    }

                    if (x.length >= 7) {
                        String remark2 = StringUtils.trimToNull(x[6]);
                        if (remark2 != null) {
                            alertMessageBuilder.remark2(remark2);
                        }
                    }

                    this.applicationEventPublisher.publishEvent(new AlertEvent(this, alertMessageBuilder.build()));

                    x = null;
                    sb = new StringBuilder();
                    log.info("actual j size: " + j);
                    if (j > 500) {
                        j = 0;
                    }

                }
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
