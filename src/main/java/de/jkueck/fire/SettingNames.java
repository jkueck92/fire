package de.jkueck.fire;

public enum SettingNames {

    SETTING_TELEGRAM_BOT_TOKEN("telegramBotToken"),
    SETTING_IS_TELEGRAM_ENABLED("isTelegramEnabled"),
    SETTING_COM_PORT("comPort");

    private String name;

    SettingNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
