package de.jkueck.fire.service.setting;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.setting.SystemSettings;

import java.util.Optional;
import java.util.Set;

public interface SystemSettingService {

    String getAsString(SystemSettings setting);

    Boolean getAsBoolean(SystemSettings setting);

    Set<SystemSetting> getAll();

    Optional<SystemSetting> findById(long id);

    Optional<SystemSetting> findByName(String name);

    Optional<SystemSetting> save(String name, String value);

    Optional<SystemSetting> update(long id, String value);



}
