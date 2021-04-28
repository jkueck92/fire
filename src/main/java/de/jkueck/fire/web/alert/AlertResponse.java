package de.jkueck.fire.web.alert;

import de.jkueck.fire.database.Alert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {

    private long id;

    private String completeMessage;

    private Date timestamp;

    public AlertResponse(Alert alert) {
        this.id = alert.getId();
        this.completeMessage = alert.getCompleteMessage();
        this.timestamp = alert.getTimestamp();
    }

}
