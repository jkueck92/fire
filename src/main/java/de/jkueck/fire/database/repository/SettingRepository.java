package de.jkueck.fire.database.repository;

import de.jkueck.fire.database.entity.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends CrudRepository<Setting, Long> {

    Optional<Setting> findByName(final String name);

}
