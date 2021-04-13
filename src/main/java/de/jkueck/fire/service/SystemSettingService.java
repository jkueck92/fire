package de.jkueck.fire.service;

import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.database.repository.SystemSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    public SystemSetting getSystemSetting(SystemSettings ss) {
        return this.systemSettingRepository.findByName(ss.getValue());
    }

    public String getSystemSettingAsString(SystemSettings ss) {
        SystemSetting systemSetting = this.getSystemSetting(ss);
        return systemSetting.getValue();
    }

    public boolean getSystemSettingAsBoolean(SystemSettings ss) {
        SystemSetting systemSetting = this.getSystemSetting(ss);
        return Boolean.parseBoolean(systemSetting.getValue());
    }

}
