package de.jkueck.fire;

import lombok.extern.log4j.Log4j2;
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

@Slf4j
@Component
public class TelegramBot implements ApplicationListener<AlertEvent> {

    @Value("${botToken}")
    private String botToken;

    @Value("${chatIds}")
    private String chatIds;

    private void sendMessage(final String text) {

        String[] chatIdData = this.chatIds.split(",");

        for (String chatId : chatIdData) {

            log.info("send message to (" + chatId + ")");

            String url = "https://api.telegram.org/bot" + this.botToken + "/sendMessage";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            SendMessage sendMessage = new SendMessage(chatId, text);
            HttpEntity<SendMessage> request = new HttpEntity<>(sendMessage, headers);

            restTemplate.postForObject(url, request, String.class);

        }
    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        log.info("receive alert (" + alertEvent.getAlertMessage().getCompleteMessage() + ")");
        if (StringUtils.trimToNull(this.botToken) != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            this.sendMessage(alertEvent.getAlertMessage().getCategory() + "/" + alertEvent.getAlertMessage().getKeyword() + "/" + sdf.format(alertEvent.getAlertMessage().getAlertTimestamp()));
        } else {
            log.warn("telegram bot is disabled due to no telegram token");
        }
    }

}
