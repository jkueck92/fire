package de.jkueck.fire.service.alert;

import de.jkueck.fire.database.Alert;
import de.jkueck.fire.database.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    public AlertServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Alert save(Alert alert) {
        return this.alertRepository.save(alert);
    }

    @Override
    public Set<Alert> getAll() {
        return StreamSupport.stream(this.alertRepository.findAll().spliterator(), Boolean.FALSE).collect(Collectors.toSet());
    }

    @Override
    public Optional<Alert> findById(long id) {
        return this.alertRepository.findById(id);
    }

}
