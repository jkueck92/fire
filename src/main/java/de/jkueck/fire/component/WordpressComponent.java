package de.jkueck.fire.component;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class WordpressComponent extends AlertBaseComponent {

    @Override
    void onReceiveAlert(AlertMessage alertMessage) {


        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT_ENCODING, StandardCharsets.UTF_8.toString());
        headers.set(HttpHeaders.AUTHORIZATION, this.getBase64AuthHeader());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://feuerwehr-ritterhude.de/wp-json/wp/v2/posts");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm");

        String image = "";

        switch (alertMessage.getCategory()) {
            case "F":
                image = "https://feuerwehr-ritterhude.de/wp-content/uploads/2021/07/fw-feuer.png";
                break;
            case "H":
                image = "https://feuerwehr-ritterhude.de/wp-content/uploads/2021/08/fw-hilfeleistung.png";
                break;
            case "C":
                log.warn("have no image for category c");
                break;
            default:

        }

        LocalDateTime localDateTime = alertMessage.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");

        builder.queryParam("title", alertMessage.getKeyword());
        builder.queryParam("content", "<figure class=\"wp-block-image size-large\"><img class=\"wp-image-3924\" src=" + image + " alt=" + alertMessage.getCategory() + "/" + alertMessage.getKeyword() + "></figure>\n" +
                "<table style=\"border-collapse: collapse;width: 100%;height: 102px\">\n" +
                "<tbody>\n" +
                "<tr style=\"height: 34px\">\n" +
                "<td style=\"width: 30%;height: 34px\"><strong>Alarmierung:</strong></td>\n" +
                "<td style=\"width: 70%;height: 34px\">" + simpleDateFormat.format(alertMessage.getTimestamp()) + " Uhr</td>\n" +
                "</tr>\n" +
                "<tr style=\"height: 34px\">\n" +
                "<td style=\"width: 30%;height: 34px\"><strong>Stichwort:</strong></td>\n" +
                "<td style=\"width: 70%;height: 34px\">" + alertMessage.getCategory() + " / " + alertMessage.getKeyword() + "</td>\n" +
                "</tr>\n" +
                "<tr style=\"height: 34px\">\n" +
                "<td style=\"width: 30%;height: 34px\"><strong>Einsatzort:</strong></td>\n" +
                "<td style=\"width: 70%;height: 34px\">Ritterhude / " + alertMessage.getStreet() + "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>");
        builder.queryParam("date", dateTimeFormatter.format(localDateTime));
        builder.queryParam("status", "future");
        builder.queryParam("categories", "1, 12");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> strResponse = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, requestEntity, String.class);

        log.info("create wordpress post for alert (" + strResponse.toString() + ")");

    }

    @Override
    boolean isComponentEnabled() {
        return this.getSystemSettingService().getAsBoolean(SystemSettings.SYSTEM_SETTING_IS_WORDPRESS_ENABLED);
    }

    private String getBase64AuthHeader() {
        String auth = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTING_WORDPRESS_USERNAME) + ":" + this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTING_WORDPRESS_PASSWORD);
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth);
    }

}
