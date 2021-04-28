package de.jkueck.fire.web.alert;

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

}
