package de.jkueck.fire.web.setting;

import de.jkueck.fire.database.SystemSetting;
import de.jkueck.fire.service.setting.SystemSettingService;
import de.jkueck.fire.web.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController extends BaseController {

    private final SystemSettingService systemSettingService;

    public SystemSettingController(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @GetMapping
    public ResponseEntity<SettingListResponse> getAll(@RequestParam(name = "sort", required = false) String sort) {
        SettingListResponse settingListResponse = new SettingListResponse();
        for (SystemSetting systemSetting : this.systemSettingService.getAll(this.getSortParameter(sort))) {
            settingListResponse.add(new SettingResponse(systemSetting));
        }
        return ResponseEntity.ok(settingListResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SettingResponse> update(@PathVariable(name = "id") Long settingId, @RequestParam(name = "value") String value) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingService.update(settingId, value);
        return optionalSystemSetting.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingResponse> get(@PathVariable(name = "id") Long settingId) {
        Optional<SystemSetting> optionalSystemSetting = this.systemSettingService.findById(settingId);
        return optionalSystemSetting.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<SettingResponse> buildResponseOk(SystemSetting setting) {
        return ResponseEntity.ok(new SettingResponse(setting));
    }

}
