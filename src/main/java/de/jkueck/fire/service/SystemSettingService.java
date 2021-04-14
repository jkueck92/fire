package de.jkueck.fire.service;

import de.jkueck.fire.SystemSettings;
import de.jkueck.fire.database.SystemSetting;

public interface SystemSettingService {

    SystemSetting getSystemSetting(SystemSettings ss);

    String getSystemSettingAsString(SystemSettings ss);

    boolean getSystemSettingAsBoolean(SystemSettings ss);

}
