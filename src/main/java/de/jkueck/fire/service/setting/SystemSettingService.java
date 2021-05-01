package de.jkueck.fire.service.setting;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.setting.SystemSettings;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface SystemSettingService {

    String getAsString(SystemSettings setting);

    Boolean getAsBoolean(SystemSettings setting);

    LinkedHashSet<SystemSetting> getAll(Sort sort);

    Optional<SystemSetting> findById(long id);

    Optional<SystemSetting> findByName(String name);

    Optional<SystemSetting> save(String name, String value);

    Optional<SystemSetting> update(long id, String value);


}
