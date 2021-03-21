package de.jkueck.fire.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "alerts")
public class Alert extends BaseEntity {

    private String completeMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Override
    public String toString() {
        return "Alert{" +
                "completeMessage='" + completeMessage + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
