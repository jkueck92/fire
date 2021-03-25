package de.jkueck.fire.database.entity;

import de.jkueck.fire.SettingNames;
import de.jkueck.fire.database.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "settings")
public class Setting extends BaseEntity {

    private SettingNames name;

    private String value;

}
