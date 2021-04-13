package de.jkueck.fire.components;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.DiveraApiParameter;
import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class DiveraComponent extends BaseComponent {

    protected DiveraComponent(SystemSettingService systemSettingService) {
        super(systemSettingService);
    }

    @Override
    void execute(AlertMessage alertMessage) {

        String diveraApiUrl = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_DIVERA_API_URL);
        String diveraAccessKey = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_DIVERA_ACCESS_KEY);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(diveraApiUrl)
                .queryParam("accessKey", diveraAccessKey)
                .queryParam(DiveraApiParameter.PARAMETER_TITLE.getValue(), alertMessage.getCategory() + "/" + alertMessage.getKeyword())
                .queryParam(DiveraApiParameter.PARAMETER_PRIORITY.getValue(), "1")
                .queryParam(DiveraApiParameter.PARAMETER_ADDRESS.getValue(), alertMessage.getStreet() + "," + alertMessage.getCity())
                .queryParam(DiveraApiParameter.PARAMETER_TEXT.getValue(), alertMessage.getKeywordText())
                .build();

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> strResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);

        log.info("send message to divera (" + builder.toUriString() + ") with access key (" + diveraAccessKey + ")");

    }

    @Override
    boolean isEnabled() {
        return this.getSystemSettingService().getSystemSettingAsBoolean(SystemSettings.SYSTEM_SETTING_IS_DIVERA_ENABLED);
    }

}
