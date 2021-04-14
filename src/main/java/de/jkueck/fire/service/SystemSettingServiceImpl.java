package de.jkueck.fire.service;

import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.database.repository.SystemSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    @Override
    public SystemSetting getSystemSetting(SystemSettings ss) {
        return this.systemSettingRepository.findByName(ss.getValue());
    }

    @Override
    public String getSystemSettingAsString(SystemSettings ss) {
        SystemSetting systemSetting = this.getSystemSetting(ss);
        return systemSetting.getValue();
    }

    @Override
    public boolean getSystemSettingAsBoolean(SystemSettings ss) {
        SystemSetting systemSetting = this.getSystemSetting(ss);
        return Boolean.parseBoolean(systemSetting.getValue());
    }

}
