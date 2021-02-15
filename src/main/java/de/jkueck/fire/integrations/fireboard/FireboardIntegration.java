package de.jkueck.fire.integrations.fireboard;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.events.AlertEvent;
import de.jkueck.fire.integrations.telegram.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Timestamp;

@Slf4j
@Component
public class FireboardIntegration implements ApplicationListener<AlertEvent> {

    @Value("${fireboardAccessKey}")
    private String fireboardAccessKey;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private void callFireboardApi(AlertMessage alertMessage) {
        FireboardPayload payload = createFBPayload(alertMessage);

        StringBuilder fireboardUrlBuilder = new StringBuilder();
        fireboardUrlBuilder.append("https://login.fireboard.net/api");
        fireboardUrlBuilder.append("?");
        fireboardUrlBuilder.append("authkey");
        fireboardUrlBuilder.append("=");
        fireboardUrlBuilder.append(this.fireboardAccessKey);
        fireboardUrlBuilder.append("&");
        fireboardUrlBuilder.append("call");
        fireboardUrlBuilder.append("=");
        fireboardUrlBuilder.append("operation_data");

        String fireboardApiUrl = fireboardUrlBuilder.toString();

        log.info("call fireboard api with url("+fireboardApiUrl+") and the following payload:"+payload.toString());
/**
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<FireboardPayload> request = new HttpEntity<FireboardPayload>(payload, headers);

        restTemplate.postForObject(fireboardApiUrl, request, String.class);
*/
    }

    /**
     * Construct the XML payload for fireboard
     * @param alertMessage The AlertMessage received from the input device
     * @return FireboardPayload Returns a Fireboard Payload Object which contains the necessary XML structure
     */
    private FireboardPayload createFBPayload(AlertMessage alertMessage) {
        FireboardPayload fbPayload = new FireboardPayload();
        PayloadBasicData fbPayloadBasicData = new PayloadBasicData();

        fbPayload.setVersion("1.01");
        fbPayload.setTest(false);
        fbPayload.setSource(this.applicationName);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        fbPayload.setUniqueID(sdf.format(timestamp));

        fbPayloadBasicData.setKeyword(alertMessage.getCategory()+alertMessage.getKeyword());
        fbPayloadBasicData.setAnnouncement(alertMessage.getRemark1()+","+alertMessage.getRemark2());
        fbPayloadBasicData.setLocation(alertMessage.getStreet()+", "+alertMessage.getCity());

        fbPayload.setBasicData(fbPayloadBasicData);

        return fbPayload;
    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        log.info("receive alert (" + alertEvent.getAlertMessage().getCompleteMessage() + ")");
        if (StringUtils.trimToNull(this.fireboardAccessKey) != null) {
            this.callFireboardApi(alertEvent.getAlertMessage());
        } else {
            log.warn("fireboard integration is disabled due to no access key");
        }
    }
}
