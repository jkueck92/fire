package de.jkueck.fire.component;

import com.fazecast.jSerialComm.SerialPort;
import de.jkueck.fire.AlertEvent;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.setting.SystemSettings;
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
public class SerialReaderComponent extends BaseComponent {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SerialReaderComponent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {

        String comPort = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTING_COM_PORT);

        log.info("start listening to (" + comPort + ")");

        SerialPort serialPort = SerialPort.getCommPort(comPort);
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

                    AlertMessage alertMessageBuilder = new AlertMessage();
                    alertMessageBuilder.setCompleteMessage(message);
                    alertMessageBuilder.setTimestamp(timestamp);

                    if (ric != null) {
                        alertMessageBuilder.setRic(ric);
                    }

                    String[] locationData = StringUtils.split(x[0], Character.toString((char) 32), 2);
                    log.info("locationData: " + locationData);

                    String city = StringUtils.trimToNull(locationData[0]);
                    log.info("city (" + city + ")");
                    if (city != null) {
                        alertMessageBuilder.setCity(city);
                    }

                    if (locationData.length == 2) {

                        String street = StringUtils.trimToNull(locationData[1]);
                        log.info("street (" + street + ")");
                        if (street != null) {
                            alertMessageBuilder.setStreet(street);
                        }

                        String object = StringUtils.trimToNull(x[1]);
                        log.info("object (" + object + ")");
                        if (object != null) {
                            alertMessageBuilder.setObject(object);
                        }

                        String category = StringUtils.trimToNull(x[2]);
                        log.info("category (" + category + ")");
                        if (category != null) {
                            alertMessageBuilder.setCategory(category);
                        }

                        String keyword = StringUtils.trimToNull(x[3]);
                        log.info("keyword (" + keyword + ")");
                        if (keyword != null) {
                            alertMessageBuilder.setKeyword(keyword);
                        }

                        String keywordText = StringUtils.trimToNull(x[4]);
                        log.info("keywordText (" + keywordText + ")");
                        if (keywordText != null) {
                            alertMessageBuilder.setKeywordText(keywordText);
                        }

                        if (x.length >= 6) {
                            String remark1 = StringUtils.trimToNull(x[5]);
                            log.info("remark1 (" + remark1 + ")");
                            if (remark1 != null) {
                                alertMessageBuilder.setRemark1(remark1);
                            }
                        }

                        if (x.length >= 7) {
                            String remark2 = StringUtils.trimToNull(x[6]);
                            log.info("remark2 (" + remark2 + ")");
                            if (remark2 != null) {
                                alertMessageBuilder.setRemark2(remark2);
                            }
                        }

                        log.info("try to publish event");
                        this.applicationEventPublisher.publishEvent(new AlertEvent(this, alertMessageBuilder));

                    } else {
                        // no street available, push standard alert

                        log.info("try to publish standard event");
                        AlertMessage alertMessage = new AlertMessage();
                        alertMessage.setCategory(" ");
                        alertMessage.setKeyword("EINSATZ");
                        alertMessage.setKeywordText("Keine Einsatzdetails vorhanden");
                        alertMessage.setStreet("Keine Standortinformationen");
                        alertMessage.setCity(" ");
                        alertMessage.setTimestamp(new Date());
                        this.applicationEventPublisher.publishEvent(new AlertEvent(this, alertMessage));
                    }

                    x = null;
                    sb = new StringBuilder();
                    log.info("actual j size: " + j);
                    if (j > 500) {
                        j = 0;
                    }


                }
            }


            log.info("close in");
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
