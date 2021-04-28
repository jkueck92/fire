package de.jkueck.fire.database.repository;

import de.jkueck.fire.database.SystemSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingRepository extends CrudRepository<SystemSetting, Long> {

    Optional<SystemSetting> findByName(final String name);

}
