package de.jkueck.fire.service;

import de.jkueck.fire.SettingNames;
import de.jkueck.fire.database.entity.Setting;
import de.jkueck.fire.database.repository.SettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class SettingService implements ISettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public Optional<Setting> save(SettingNames name, String value) {
        return Optional.of(this.save(new Setting(name, value)));
    }

    @Override
    public Optional<Setting> save(SettingNames name, Boolean value) {
        return Optional.of(this.save(new Setting(name, String.valueOf(value))));
    }

    @Override
    public Optional<Setting> save(SettingNames name, Integer value) {
        return Optional.of(this.save(new Setting(name, String.valueOf(value))));
    }

    @Override
    public Optional<Setting> save(SettingNames name, Double value) {
        return Optional.of(this.save(new Setting(name, String.valueOf(value))));
    }

    @Override
    public Optional<Setting> findById(long id) {
        return this.settingRepository.findById(id);
    }

    @Override
    public Optional<Setting> findByName(String name) {
        return this.settingRepository.findByName(name);
    }

    @Override
    public Optional<Setting> findByName(SettingNames name) {
        return this.settingRepository.findByName(name.getName());
    }

    @Override
    public Optional<Setting> update(long id, String value) {
        Optional<Setting> optionalSetting = this.findById(id);
        if (optionalSetting.isPresent()) {
            optionalSetting.get().setValue(value);
            return Optional.of(this.settingRepository.save(optionalSetting.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Set<Setting>> findAll() {
        Set<Setting> settings = new HashSet<>();
        this.settingRepository.findAll().forEach(settings::add);
        return Optional.of(settings);
    }

    private Setting save(Setting setting) {
        return this.settingRepository.save(setting);
    }

}
