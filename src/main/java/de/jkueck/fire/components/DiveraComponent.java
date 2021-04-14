package de.jkueck.fire.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.DiveraApiParameter;
import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.osm.OsmAddressResponse;
import de.jkueck.fire.service.SystemSettingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@Slf4j
@Component
public class DiveraComponent extends AlertBaseComponent {

    protected DiveraComponent(SystemSettingServiceImpl systemSettingServiceImpl) {
        super(systemSettingServiceImpl);
    }

    @Override
    void execute(AlertMessage alertMessage) {

        String diveraApiUrl = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_DIVERA_API_URL);
        String diveraAccessKey = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_DIVERA_ACCESS_KEY);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(diveraApiUrl)
                .queryParam("accessKey", diveraAccessKey)
                .queryParam(DiveraApiParameter.PARAMETER_TITLE.getValue(), alertMessage.getCategory() + "/" + alertMessage.getKeyword())
                .queryParam(DiveraApiParameter.PARAMETER_PRIORITY.getValue(), "1")
                .queryParam(DiveraApiParameter.PARAMETER_ADDRESS.getValue(), alertMessage.getStreet() + "," + alertMessage.getCity())
                .queryParam(DiveraApiParameter.PARAMETER_TEXT.getValue(), alertMessage.getKeywordText());


        OsmAddressResponse osmAddressResponse = this.getAddressCoordinates(alertMessage.getCity(), alertMessage.getStreet(), alertMessage.getObject());
        if (osmAddressResponse != null) {
            builder.queryParam(DiveraApiParameter.PARAMETER_LAT.getValue(), osmAddressResponse.getLat());
            builder.queryParam(DiveraApiParameter.PARAMETER_LNG.getValue(), osmAddressResponse.getLon());
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> strResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);

        log.info("send message to divera (" + builder.toUriString() + ") with access key (" + diveraAccessKey + ")");

    }

    @Override
    boolean isEnabled() {
        return this.getSystemSettingService().getSystemSettingAsBoolean(SystemSettings.SYSTEM_SETTING_IS_DIVERA_ENABLED);
    }

    private OsmAddressResponse getAddressCoordinates(String city, String street, String object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            String cityWithZip = "";
            if (city.equalsIgnoreCase("Ritterhude")) {
                cityWithZip = "27721 Ritterhude";
            } else {
                cityWithZip = city;
            }

            OsmAddressResponse[] osmAddressResponseList = objectMapper.readValue(new URL("https://nominatim.openstreetmap.org/search/" + street + " " + cityWithZip + "?format=json&limit=1"), OsmAddressResponse[].class);
            if (Arrays.stream(osmAddressResponseList).findFirst().isPresent()) {
                return Arrays.stream(osmAddressResponseList).findFirst().get();
            } else {
                log.warn("can not find coordinates to address (" + city + ", " + street + ", " + object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
