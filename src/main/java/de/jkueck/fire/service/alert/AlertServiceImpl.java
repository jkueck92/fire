package de.jkueck.fire.service.alert;

import de.jkueck.fire.database.Alert;
import de.jkueck.fire.database.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    public AlertServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> save(Alert alert) {
        return Optional.of(this.alertRepository.save(alert));
    }

    @Override
    public Set<Alert> getAll() {
        return new HashSet<>(this.alertRepository.findAll());
    }

    @Override
    public LinkedHashSet<Alert> getAll(Sort sort) {
        return new LinkedHashSet<>(this.alertRepository.findAll(sort));
    }

    @Override
    public Optional<Alert> findById(long id) {
        return this.alertRepository.findById(id);
    }

}
