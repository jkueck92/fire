package de.jkueck.fire.web.setting;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.service.setting.SystemSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingService systemSettingService;

    public SystemSettingController(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @GetMapping
    public ResponseEntity<SettingListResponse> getAllSettings() {
        SettingListResponse settingListResponse = new SettingListResponse();
        for (SystemSetting systemSetting : this.systemSettingService.getAll()) {
            settingListResponse.add(new SettingResponse(systemSetting.getId(), systemSetting.getName(), systemSetting.getValue(), systemSetting.getUpdatedAt()));
        }
        return ResponseEntity.ok(settingListResponse);
    }

    @PutMapping("/{settingId}")
    public ResponseEntity<SettingResponse> updateSetting(@PathVariable(name = "settingId") Long settingId, @RequestParam(name = "value") String value) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingService.update(settingId, value);
        return optionalSystemSetting.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{settingId}")
    public ResponseEntity<SettingResponse> getSetting(@PathVariable(name = "settingId") Long settingId) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingService.findById(settingId);
        return optionalSystemSetting.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<SettingResponse> buildResponseOk(SystemSetting setting) {
        return ResponseEntity.ok(new SettingResponse(setting.getId(), setting.getName(), setting.getValue(), setting.getUpdatedAt()));
    }

}
