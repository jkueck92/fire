package de.jkueck.fire.service;

import de.jkueck.fire.SettingNames;
import de.jkueck.fire.database.entity.Setting;

import java.util.Optional;
import java.util.Set;

public interface ISettingService {

    Optional<Setting> save(SettingNames name, String value);

    Optional<Setting> save(SettingNames name, Boolean value);

    Optional<Setting> save(SettingNames name, Integer value);

    Optional<Setting> save(SettingNames name, Double value);

    Optional<Setting> findById(final long id);

    Optional<Setting> findByName(final String name);

    Optional<Setting> findByName(SettingNames name);

    Optional<Setting> update(final long id, final String value);

    Optional<Set<Setting>> findAll();
    
}
