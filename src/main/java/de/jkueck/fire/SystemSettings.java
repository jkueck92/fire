package de.jkueck.fire;

import de.jkueck.fire.database.SystemSetting;
import lombok.Getter;

import java.util.LinkedHashMap;

public class SystemSettings {

    public static final String SYSTEM_SETTING_APPLICATION_NAME = "applicationName";
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
        this.addName(SYSTEM_SETTING_APPLICATION_NAME, "fire", "fire");
        this.addName(SYSTEM_SETTING_TELEGRAM_BOT_ID, "1234-telegram", "1234-telegram");
        this.addName(SYSTEM_SETTING_DIVERA_ACCESS_KEY, "1234-divera", "1234-divera");
        this.addName(SYSTEM_SETTING_IS_TELEGRAM_ENABLED, "true", "false");
        this.addName(SYSTEM_SETTING_IS_DIVERA_ENABLED, "true", "false");
        this.addName(SYSTEM_SETTING_DIVERA_MESSAGE, "lorem ipsum", "lorem ipsum");
        this.addName(SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm", "dd.MM.yyyy HH:mm");
        this.addName(SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED, "true", "false");
        this.addName(SYSTEM_SETTING_COM_PORT, "xxx", "xxx");
    }

    private void addName(String name, String value, String defaultValue) {
        this.systemSettingMap.put(name, new SystemSetting(name, value, defaultValue));
    }

}
