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

    /* public static final String SYSTEM_SETTING_APPLICATION_NAME = "applicationName";
    public static final String SYSTEM_SETTING_TELEGRAM_BOT_ID = "telegramBotId";
    public static final String SYSTEM_SETTING_IS_TELEGRAM_ENABLED = "isTelegramEnabled";
    public static final String SYSTEM_SETTING_DIVERA_ACCESS_KEY = "diveraAccessKey";
    public static final String SYSTEM_SETTING_IS_DIVERA_ENABLED = "isDiveraEnabled";
    public static final String SYSTEM_SETTING_DIVERA_MESSAGE = "diveraMessage";
    public static final String SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT = "alertMessageDateTimeFormat";
    public static final String SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED = "isDatabasePersistEnabled";
    public static final String SYSTEM_SETTING_COM_PORT = "comPort";

    @Getter
    private final LinkedHashMap<String, SystemSetting> systemSettingMap = new LinkedHashMap<>();

    public SystemSettings() {
        this.addName(SYSTEM_SETTING_APPLICATION_NAME, "fire");
        this.addName(SYSTEM_SETTING_TELEGRAM_BOT_ID, "1234-telegram");
        this.addName(SYSTEM_SETTING_DIVERA_ACCESS_KEY, "1234-divera");
        this.addName(SYSTEM_SETTING_IS_TELEGRAM_ENABLED, "true");
        this.addName(SYSTEM_SETTING_IS_DIVERA_ENABLED, "true");
        this.addName(SYSTEM_SETTING_DIVERA_MESSAGE, "lorem ipsum");
        this.addName(SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm");
        this.addName(SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED, "true");
        this.addName(SYSTEM_SETTING_COM_PORT, "xxx");
    }

    private void addName(String name, String value) {
        this.systemSettingMap.put(name, new SystemSetting(name, value));
    } */

}
