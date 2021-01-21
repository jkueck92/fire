package de.jkueck.fire.bots.divera;

import de.jkueck.fire.events.AlertEvent;
import de.jkueck.fire.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class DiveraBot implements ApplicationListener<AlertEvent> {

    @Value("${diveraToken}")
    private String diveraAccessKey;

    private void callDiveraApi(AlertMessage alertMessage) {

        StringBuilder diveraUrlBuilder = new StringBuilder();
        diveraUrlBuilder.append("https://www.divera247.com/api/alarm");
        diveraUrlBuilder.append("?");
        diveraUrlBuilder.append("title");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append("F/01");
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("priority=true");
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("text");
        diveraUrlBuilder.append(alertMessage.getCompleteMessage());
        diveraUrlBuilder.append("xx");
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("coordinates=gk");
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("accesskey");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append(this.diveraAccessKey);

        log.info("call divera api with url (" + diveraUrlBuilder.toString() + ")");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(diveraUrlBuilder.toString(), String.class);
        log.info(String.valueOf(responseEntity.getStatusCode().value()));

    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        log.info("receive alert (" + alertEvent.getAlertMessage().getCompleteMessage() + ")");
        if (StringUtils.trimToNull(this.diveraAccessKey) != null) {
            this.callDiveraApi(alertEvent.getAlertMessage());
        } else {
            log.warn("divera bot is disabled due to no divera token");
        }
    }

}
