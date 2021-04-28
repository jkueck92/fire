package de.jkueck.fire.service.alert;

import de.jkueck.fire.database.Alert;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public interface AlertService {

    /**
     * Save a new {@link Alert}.
     *
     * @param alert {@link Alert}
     * @return Optional with new {@link Alert}
     */
    Optional<Alert> save(Alert alert);

    /**
     * Get a set of all available {@link Alert}.
     *
     * @return Set of {@link Alert}
     */
    Set<Alert> getAll();

    /**
     * Get a set of all available {@link Alert} with sort argument
     *
     * @param sort Sort
     * @return Set of {@link Alert}
     */
    LinkedHashSet<Alert> getAll(Sort sort);

    /**
     * Get a single {@link Alert} identified by its id.
     *
     * @param id Id of the {@link Alert}
     * @return Optional with {@link Alert}
     */
    Optional<Alert> findById(long id);

}
