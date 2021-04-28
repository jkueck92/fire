package de.jkueck.fire.component;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.DiveraApiParameter;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DiveraComponent extends AlertBaseComponent {

    @Override
    void onReceiveAlert(AlertMessage alertMessage) {

        String diveraApiUrl = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTINGS_DIVERA_API_URL);
        String diveraAccessKey = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTINGS_DIVERA_ACCESS_KEY);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT_ENCODING, StandardCharsets.UTF_8.toString());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(diveraApiUrl)
                .queryParam("accesskey", diveraAccessKey)
                .queryParam(DiveraApiParameter.PARAMETER_TITLE.getValue(), alertMessage.getCategory() + alertMessage.getKeyword())
                .queryParam(DiveraApiParameter.PARAMETER_PRIORITY.getValue(), "1")
                .queryParam(DiveraApiParameter.PARAMETER_ADDRESS.getValue(), this.getAlertAddressInformation(alertMessage))
                .queryParam(DiveraApiParameter.PARAMETER_TEXT.getValue(), this.getAlertInformation(alertMessage));

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> strResponse = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, requestEntity, String.class);

        log.info("send message to divera (" + builder.build().toUri() + ") with access key (" + diveraAccessKey + "), response (" + strResponse + ")");

    }

    @Override
    boolean isComponentEnabled() {
        return this.getSystemSettingService().getAsBoolean(SystemSettings.SYSTEM_SETTINGS_IS_DIVERA_ENABLED);
    }

    private String getAlertAddressInformation(AlertMessage alertMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(alertMessage.getStreet());
        stringBuilder.append(", ");
        if (StringUtils.isNotBlank(alertMessage.getObject())) {
            stringBuilder.append(alertMessage.getObject());
            stringBuilder.append(", ");
        }
        stringBuilder.append(alertMessage.getCity());
        return stringBuilder.toString();
    }

    private String getAlertInformation(AlertMessage alertMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(alertMessage.getKeywordText())) {
            stringBuilder.append(alertMessage.getKeywordText());
        } else {
            stringBuilder.append("Keine Stichwortbeschreibung vorhanden");
        }

        if (StringUtils.isNotBlank(alertMessage.getRemark1())) {
            stringBuilder.append(" - ");
            stringBuilder.append(alertMessage.getRemark1());
        }

        if (StringUtils.isNotBlank(alertMessage.getRemark2())) {
            stringBuilder.append(" - ");
            stringBuilder.append(alertMessage.getRemark2());
        }
        return stringBuilder.toString();
    }

}
