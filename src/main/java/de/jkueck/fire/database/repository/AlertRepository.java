package de.jkueck.fire.database.repository;

import de.jkueck.fire.database.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<Alert, Long> {



}
