package de.jkueck.fire.setting;

import lombok.Getter;

public enum SystemSettings {

    SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT("alertMessageDateTimeFormat", "dd.MM.yyyy HH:mm"),
    SYSTEM_SETTING_TELEGRAM_BOT_ID("telegramBotId", "12345"),
    SYSTEM_SETTINGS_IS_TELEGRAM_ENABLED("isTelegramEnabled", "false"),
    SYSTEM_SETTINGS_IS_DATABASE_ENABLED("isDatabaseEnabled", "false"),
    SYSTEM_SETTINGS_IS_DIVERA_ENABLED("isDiveraEnabled", "false"),
    SYSTEM_SETTINGS_DIVERA_API_URL("diveraApiUrl", "https://api.divera.de"),
    SYSTEM_SETTINGS_DIVERA_ACCESS_KEY("diveraAccessKey", "12345"),
    SYSTEM_SETTING_COM_PORT("comPort", "13"),
    SYSTEM_SETTING_STANDARD_RIC("standardRIC", "EinsatzB,"),
    SYSTEM_SETTING_IS_WORDPRESS_ENABLED("isWOrdpressEnabled", "false"),
    SYSTEM_SETTING_WORDPRESS_USERNAME("wordPressUsername", "1234"),
    SYSTEM_SETTING_WORDPRESS_PASSWORD("wordpressPassword", "1234");

    @Getter
    private String value;

    @Getter
    private String defaultValue;

    SystemSettings(String value, String defaultValue) {
        this.value = value;
        this.defaultValue = defaultValue;
    }
}
