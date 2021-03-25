package de.jkueck.fire.service;

import de.jkueck.fire.database.entity.Alert;
import de.jkueck.fire.database.repository.AlertRepository;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void save(Alert alert) {
        this.alertRepository.save(alert);
    }

}
