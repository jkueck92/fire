package de.jkueck.fire.components;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.SendMessage;
import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.service.TelegramChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TelegramComponent extends AlertBaseComponent {

    private final TelegramChatService telegramChatService;

    protected TelegramComponent(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @Override
    void execute(AlertMessage alertMessage) {

        Optional<Set<TelegramChat>> optionalTelegramChats = this.telegramChatService.getEnabledTelegramChats();
        if (optionalTelegramChats.isPresent()) {

            for (TelegramChat telegramChat : optionalTelegramChats.get()) {

                if (telegramChat.getMessage() != null) {

                    StringBuffer stringBuffer = new StringBuffer();

                    Matcher matcher = Pattern.compile("\\{(.*?)}").matcher(telegramChat.getMessage());

                    String format = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

                    while (matcher.find()) {

                        String toReplace = matcher.group(1);

                        switch (toReplace) {
                            case AlertMessage.PLACEHOLDER_CITY:
                                matcher.appendReplacement(stringBuffer, alertMessage.getCity());
                                break;
                            case AlertMessage.PLACEHOLDER_STREET:
                                matcher.appendReplacement(stringBuffer, alertMessage.getStreet());
                                break;
                            case AlertMessage.PLACEHOLDER_OBJECT:
                                matcher.appendReplacement(stringBuffer, alertMessage.getObject());
                                break;
                            case AlertMessage.PLACEHOLDER_CATEGORY:
                                matcher.appendReplacement(stringBuffer, alertMessage.getCategory());
                                break;
                            case AlertMessage.PLACEHOLDER_KEYWORD:
                                matcher.appendReplacement(stringBuffer, alertMessage.getKeyword());
                                break;
                            case AlertMessage.PLACEHOLDER_KEYWORD_TEXT:
                                matcher.appendReplacement(stringBuffer, alertMessage.getKeywordText());
                                break;
                            case AlertMessage.PLACEHOLDER_REMARK_1:
                                matcher.appendReplacement(stringBuffer, alertMessage.getRemark1());
                                break;
                            case AlertMessage.PLACEHOLDER_REMARK_2:
                                matcher.appendReplacement(stringBuffer, alertMessage.getRemark2());
                                break;
                            case AlertMessage.PLACEHOLDER_TIMESTAMP:
                                matcher.appendReplacement(stringBuffer, simpleDateFormat.format(alertMessage.getTimestamp()));
                                break;
                        }

                    }

                    matcher.appendTail(stringBuffer);

                    RestTemplate restTemplate = new RestTemplate();

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                    SendMessage sendMessage = SendMessage.builder().chatId(telegramChat.getChatId()).text(stringBuffer.toString()).build();

                    HttpEntity<SendMessage> request = new HttpEntity<>(sendMessage, httpHeaders);

                    String telegramBotId = this.getSystemSettingService().getSystemSettingAsString(SystemSettings.SYSTEM_SETTING_TELEGRAM_BOT_ID);

                    restTemplate.patchForObject("https://api.telegram.org/bot" + telegramBotId + "/sendMessage", request, String.class);

                    log.info("send message (" + stringBuffer + ") to telegram chat (" + telegramChat.getChatId() + ") with telegram bot id (" + telegramBotId + ")");

                }

            }

        }

    }

    @Override
    boolean isEnabled() {
        return this.getSystemSettingService().getSystemSettingAsBoolean(SystemSettings.SYSTEM_SETTING_IS_TELEGRAM_ENABLED);
    }

}
