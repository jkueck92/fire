package de.jkueck.fire.database.repository;

import de.jkueck.fire.database.SystemSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingRepository extends CrudRepository<SystemSetting, Long> {

    SystemSetting findByName(final String name);

}
