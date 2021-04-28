package de.jkueck.fire.service.setting;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.database.repository.SystemSettingRepository;
import de.jkueck.fire.setting.SystemSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class SystemSettingServiceImpl  implements SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    // TODO return Optional, not null
    @Override
    public String getAsString(SystemSettings setting) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingRepository.findByName(setting.getValue());
        return optionalSystemSetting.map(SystemSetting::getValue).orElse(null);
    }

    @Override
    public Boolean getAsBoolean(SystemSettings setting) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingRepository.findByName(setting.getValue());
        return optionalSystemSetting.map(systemSetting -> Boolean.parseBoolean(systemSetting.getValue())).orElse(Boolean.FALSE);
    }

    @Override
    public Set<SystemSetting> getAll() {
        return StreamSupport.stream(this.systemSettingRepository.findAll().spliterator(), Boolean.FALSE).collect(Collectors.toSet());
    }

    @Override
    public Optional<SystemSetting> findById(long id) {
        return this.systemSettingRepository.findById(id);
    }

    @Override
    public Optional<SystemSetting> findByName(String name) {
        return this.systemSettingRepository.findByName(name);
    }

    @Override
    public Optional<SystemSetting> save(String name, String value) {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setName(name);
        systemSetting.setValue(value);

        SystemSetting dbSystemSetting = this.systemSettingRepository.save(systemSetting);
        return Optional.of(dbSystemSetting);
    }

    @Override
    public Optional<SystemSetting> update(long id, String value) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingRepository.findById(id);
        if (optionalSystemSetting.isPresent()) {
            optionalSystemSetting.get().setValue(value);
            SystemSetting dbSystemSetting = this.systemSettingRepository.save(optionalSystemSetting.get());
            return Optional.of(dbSystemSetting);
        }
        return Optional.empty();
    }

}
