package de.jkueck.fire.component;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.SendMessage;
import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.service.chat.TelegramChatService;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TelegramComponent extends AlertBaseComponent {

    private final TelegramChatService telegramChatService;

    public TelegramComponent(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @Override
    void onReceiveAlert(AlertMessage alertMessage) {

        Set<TelegramChat> telegramChats = this.telegramChatService.getEnabledTelegramChats();

        if (!telegramChats.isEmpty()) {

            for (TelegramChat telegramChat : telegramChats) {

                log.info("process telegram chat (" + telegramChat.getChatId() + ")");

                Map<String, String> values = new HashMap<>();

                values.put(AlertMessage.PLACEHOLDER_CITY, this.checkPlaceholder(alertMessage.getCity()));
                values.put(AlertMessage.PLACEHOLDER_STREET, this.checkPlaceholder(alertMessage.getStreet()));
                values.put(AlertMessage.PLACEHOLDER_OBJECT, this.checkPlaceholder(alertMessage.getObject()));
                values.put(AlertMessage.PLACEHOLDER_CATEGORY, this.checkPlaceholder(alertMessage.getCategory()));
                values.put(AlertMessage.PLACEHOLDER_KEYWORD, this.checkPlaceholder(alertMessage.getKeyword()));
                values.put(AlertMessage.PLACEHOLDER_KEYWORD_TEXT, this.checkPlaceholder(alertMessage.getKeywordText()));
                values.put(AlertMessage.PLACEHOLDER_REMARK_1, this.checkPlaceholder(alertMessage.getRemark1()));
                values.put(AlertMessage.PLACEHOLDER_REMARK_2, this.checkPlaceholder(alertMessage.getRemark2()));

                StringSubstitutor substitutor = new StringSubstitutor(values, "{", "}");

                String format = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT);
                if (format == null) {
                    format = "dd.MM.yyyy HH:mm";
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

                values.put(AlertMessage.PLACEHOLDER_TIMESTAMP, simpleDateFormat.format(alertMessage.getTimestamp()));

                String result = substitutor.replace(telegramChat.getMessage());

                log.info("replaced message (" + result + ")");

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                SendMessage sendMessage = SendMessage.builder().chatId(telegramChat.getChatId()).text(result).build();

                HttpEntity<SendMessage> request = new HttpEntity<>(sendMessage, httpHeaders);

                String telegramBotId = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTING_TELEGRAM_BOT_ID);

                log.info("send message (" + result + ") to telegram chat (" + telegramChat.getChatId() + ") with telegram bot id (" + telegramBotId + ")");

                restTemplate.postForObject("https://api.telegram.org/bot" + telegramBotId + "/sendMessage", request, String.class);

            }

        } else {
            log.warn("found no telegram chats in database");
        }
    }

    private String checkPlaceholder(String value) {
        if (StringUtils.trimToNull(value) == null) {
            return "";
        }
        return value;
    }

    @Override
    boolean isComponentEnabled() {
        return this.getSystemSettingService().getAsBoolean(SystemSettings.SYSTEM_SETTINGS_IS_TELEGRAM_ENABLED);
    }
}
