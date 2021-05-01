package de.jkueck.fire.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "system_settings")
public class SystemSetting extends BaseEntity {

    private String name;

    private String value;

    @Override
    public String toString() {
        return "SystemSetting{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
