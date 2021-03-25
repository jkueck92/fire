package de.jkueck.fire.database;

import de.jkueck.fire.SettingNames;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class SettingNamesComparator implements AttributeConverter<SettingNames, String> {

    @Override
    public String convertToDatabaseColumn(SettingNames name) {
        return name.getName();
    }

    @Override
    public SettingNames convertToEntityAttribute(String name) {
        return Arrays.stream(SettingNames.values()).filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

}
