package de.jkueck.fire.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingListResponse {

    private Set<SettingResponse> settings = new HashSet<>();

    public void add(SettingResponse settingResponse) {
        this.settings.add(settingResponse);
    }

}
