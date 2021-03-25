package de.jkueck.fire;

public enum SettingNames {

    SETTING_TELEGRAM_BOT_TOKEN("telegramBotToken"),
    SETTING_IS_TELEGRAM_ENABLED("isTelegramEnabled"),
    SETTING_IS_DIVERA_ENABLED("isDiveraEnabled"),
    SETTING_DIVERA_ACCESS_TOKEN("diveraAccessToken"),
    SETTING_ALERT_MESSAGE_TIMESTAMP_FORMAT("alertMessageTimestampFormat"),
    SETTING_COM_PORT("comPort");

    private String name;

    SettingNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
