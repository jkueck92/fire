package de.jkueck.fire.service;

import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.database.repository.SystemSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    /**
     *
     */
    public void initSettings() {

        SystemSettings systemSettings = new SystemSettings();

        for (String key : systemSettings.getSystemSettingMap().keySet()) {

            Optional<SystemSetting> optionalSystemSetting = this.getSystemSettingByName(key);
            if (optionalSystemSetting.isPresent()) {
                log.info("found SystemSetting (" + optionalSystemSetting.get() + ")");
            } else {
                SystemSetting systemSetting = systemSettings.getSystemSettingMap().get(key);
                this.systemSettingRepository.save(systemSetting);
                log.info("add new SystemSetting (" + systemSetting + ")");
            }

        }

    }

    /**
     * @return
     */
    public boolean isDatabasePersistEnabled() {
        return this.getBooleanValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_IS_DATABASE_PERSIST_ENABLED));
    }

    /**
     * @return
     */
    public boolean isTelegramEnabled() {
        return this.getBooleanValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_IS_TELEGRAM_ENABLED));
    }

    /**
     * @return
     */
    public boolean isDiveraEnabled() {
        return this.getBooleanValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_IS_DIVERA_ENABLED));
    }

    /**
     * @return
     */
    public String getDiveraAccessKey() {
        return this.getStringValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_DIVERA_ACCESS_KEY));
    }

    /**
     * @return
     */
    public String getTelegramBotId() {
        return this.getStringValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_TELEGRAM_BOT_ID));
    }

    /**
     * @return
     */
    public String getComPort() {
        return this.getStringValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_COM_PORT));
    }

    /**
     * @return
     */
    public String getDiveraMessage() {
        return this.getStringValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_DIVERA_MESSAGE));
    }

    /**
     * @return
     */
    public String getAlertMessageTimestampFormat() {
        return this.getStringValue(this.getSystemSettingByName(SystemSettings.SYSTEM_SETTING_ALERT_MESSAGE_DATE_TIME_FORMAT));
    }

    /**
     * @param name
     * @return
     */
    public Optional<SystemSetting> getSystemSettingByName(final String name) {
        SystemSetting systemSetting = this.systemSettingRepository.findByName(name);
        if (systemSetting != null) {
            return Optional.of(systemSetting);
        }
        return Optional.empty();
    }

    /**
     * @param optionalSystemSetting
     * @return
     */
    private String getStringValue(Optional<SystemSetting> optionalSystemSetting) {
        if (optionalSystemSetting.isPresent()) {
            SystemSetting systemSetting = optionalSystemSetting.get();
            if (systemSetting.getValue() != null) {
                return systemSetting.getValue();
            } else {
                return systemSetting.getDefaultValue();
            }
        }
        return "";
    }

    /**
     * @param optionalSystemSetting
     * @return
     */
    private boolean getBooleanValue(Optional<SystemSetting> optionalSystemSetting) {
        if (optionalSystemSetting.isPresent()) {
            SystemSetting systemSetting = optionalSystemSetting.get();
            if (systemSetting.getValue() != null) {
                return Boolean.parseBoolean(systemSetting.getValue());
            } else {
                return Boolean.parseBoolean(systemSetting.getDefaultValue());
            }
        }
        return Boolean.FALSE;
    }

}
