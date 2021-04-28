package de.jkueck.fire.web.alert;

import de.jkueck.fire.database.Alert;
import de.jkueck.fire.service.alert.AlertService;
import de.jkueck.fire.web.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
public class AlertController extends BaseController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<AlertListResponse> getAll(@RequestParam(name = "sort", required = false) String sort) {
        AlertListResponse alertListResponse = new AlertListResponse();
        for (Alert alert : this.alertService.getAll(this.getSortParameter(sort))) {
            alertListResponse.add(new AlertResponse(alert.getId(), alert.getCompleteMessage(), alert.getTimestamp()));
        }
        return ResponseEntity.ok(alertListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> get(@PathVariable(name = "id") Long id) {
        Optional<Alert> optionalAlert = this.alertService.findById(id);
        return optionalAlert.map(alert -> ResponseEntity.ok(new AlertResponse(alert.getId(), alert.getCompleteMessage(), alert.getTimestamp()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
