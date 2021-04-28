package de.jkueck.fire.web.alert;

import de.jkueck.fire.database.Alert;
import de.jkueck.fire.service.alert.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<AlertListResponse> getAll() {
        AlertListResponse alertListResponse = new AlertListResponse();
        for (Alert alert : this.alertService.getAll()) {
            alertListResponse.add(new AlertResponse(alert.getId(), alert.getCompleteMessage(), alert.getTimestamp()));
        }
        return ResponseEntity.ok(alertListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> getAlert(@PathVariable(name = "id") Long id) {
        Optional<Alert> optionalAlert = this.alertService.findById(id);
        return optionalAlert.map(alert -> ResponseEntity.ok(new AlertResponse(alert.getId(), alert.getCompleteMessage(), alert.getTimestamp()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
