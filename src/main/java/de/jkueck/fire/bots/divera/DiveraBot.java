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
        diveraUrlBuilder.append(alertMessage.getCategory());
        diveraUrlBuilder.append(alertMessage.getKeyword());
        diveraUrlBuilder.append(" ");
        diveraUrlBuilder.append(alertMessage.getKeyword());
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("priority=true");
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("address");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append(alertMessage.getStreet());
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("text");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append(alertMessage.getCompleteMessage());
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("scene_object");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append(alertMessage.getObject());
        diveraUrlBuilder.append("&");
        diveraUrlBuilder.append("accesskey");
        diveraUrlBuilder.append("=");
        diveraUrlBuilder.append(this.diveraAccessKey);

        log.info("call divera api with url: (" + diveraUrlBuilder.toString() + ")");

        // https://www.divera247.com/api/alarm?title=F011%20Testfeuer&priority=true&text=Musterstadt%20Musterstra%C3%9Fe%2012%2FMusterfimra%2FF%2F01%2Fmschine%20brennt%2Ftestcomment1%2Ftestcomment2&address=Musterstra%C3%9Fe%2012&scene_object=Musterfimra&additional_text_1=test%20comment%201&additional_text_2=test%20comment%202&coordinates=gk&accesskey=cU3GYfdXOQicyr16PxPdkYkYDaYReUyi_KpIx90fnhlaZKbG_HgLippXZgGFNJCz

        // RestTemplate restTemplate = new RestTemplate();
        // ResponseEntity<String> responseEntity = restTemplate.getForEntity(diveraUrlBuilder.toString(), String.class);
        // log.info(String.valueOf(responseEntity.getStatusCode().value()));

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
