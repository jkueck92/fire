package de.jkueck.fire.service.alert;

import de.jkueck.fire.database.Alert;

import java.util.Optional;
import java.util.Set;

public interface AlertService {

    Alert save(Alert alert);

    Set<Alert> getAll();

    Optional<Alert> findById(long id);

}
