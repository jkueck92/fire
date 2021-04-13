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

    public SystemSetting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
