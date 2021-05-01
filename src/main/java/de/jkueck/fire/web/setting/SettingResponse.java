package de.jkueck.fire.web.setting;

import de.jkueck.fire.database.SystemSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponse {

    private long id;

    private String name;

    private String value;

    private Date lastChange;

    public SettingResponse(SystemSetting systemSetting) {
        this.id = systemSetting.getId();
        this.name = systemSetting.getName();
        this.value = systemSetting.getValue();
        this.lastChange = systemSetting.getUpdatedAt();
    }

}
