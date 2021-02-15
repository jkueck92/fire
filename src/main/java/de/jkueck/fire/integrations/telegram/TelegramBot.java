package de.jkueck.fire.integrations.telegram;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.events.AlertEvent;
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

    @Value("${messageFormat}")
    private String messageFormat;

    @Value("${botToken}")
    private String botToken;

    @Value("${chatIds}")
    private String chatIds;

    private void sendMessage(AlertMessage alertMessage) {

        String[] messageFormatData = messageFormat.split(",");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        StringBuilder builder = new StringBuilder();
        builder.append(alertMessage.getCategory());
        builder.append("/");
        builder.append(alertMessage.getKeyword());
        builder.append("/");
        builder.append(alertMessage.getKeywordText());
        builder.append("/");
        builder.append(sdf.format(alertMessage.getAlertTimestamp()));

        String[] chatIdData = this.chatIds.split(",");

        for (String chatId : chatIdData) {

            log.info("send message to (" + chatId + ")");

            String url = "https://api.telegram.org/bot" + this.botToken + "/sendMessage";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            SendMessage sendMessage = new SendMessage(chatId, builder.toString());
            HttpEntity<SendMessage> request = new HttpEntity<>(sendMessage, headers);

            restTemplate.postForObject(url, request, String.class);

        }
    }

    @Override
    public void onApplicationEvent(AlertEvent alertEvent) {
        log.info("receive alert (" + alertEvent.getAlertMessage().getCompleteMessage() + ")");
        if (StringUtils.trimToNull(this.botToken) != null) {
            this.sendMessage(alertEvent.getAlertMessage());
        } else {
            log.warn("telegram bot is disabled due to no telegram token");
        }
    }

}
