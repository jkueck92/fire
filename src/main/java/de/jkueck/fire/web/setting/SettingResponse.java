package de.jkueck.fire.web.setting;

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

}
