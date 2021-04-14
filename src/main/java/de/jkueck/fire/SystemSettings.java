package de.jkueck.fire;

import lombok.Getter;

public enum SystemSettings {

    SYSTEM_SETTING_APPLICATION_NAME("applicationName"),
    SYSTEM_SETTING_TELEGRAM_BOT_ID("telegramBotId"),
    SYSTEM_SETTING_IS_TELEGRAM_ENABLED("isTelegramEnabled"),
    SYSTEM_SETTING_DIVERA_ACCESS_KEY("diveraAccessKey"),
    SYSTEM_SETTING_IS_DIVERA_ENABLED("isDiveraEnabled"),
    SYSTEM_SETTING_DIVERA_MESSAGE("diveraMessage"),
    SYSTEM_SETTING_DIVERA_API_URL("diveraApiUrl"),
    SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT("alertMessageDateTimeFormat"),
    SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED("isDatabasePersistEnabled"),
    SYSTEM_SETTING_COM_PORT("comPort");

    @Getter
    private String value;

    SystemSettings(String value) {
        this.value = value;
    }

}
