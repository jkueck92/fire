package de.jkueck.fire.database;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "system_settings")
public class SystemSetting extends BaseEntity {

    private String name;

    private String value;

    private String defaultValue;

    public SystemSetting(String name, String value, String defaultValue) {
        this.name = name;
        this.value = value;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }

}
