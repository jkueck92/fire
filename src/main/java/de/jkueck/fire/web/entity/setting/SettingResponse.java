package de.jkueck.fire.web.entity.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponse {

    private long id;

    private String name;

    private String value;

}
