package de.jkueck.fire.web.controller;

import de.jkueck.fire.SettingNames;
import de.jkueck.fire.database.entity.Setting;
import de.jkueck.fire.service.ISettingService;
import de.jkueck.fire.web.entity.SettingListResponse;
import de.jkueck.fire.web.entity.SettingRequest;
import de.jkueck.fire.web.entity.SettingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
public class SettingController {

    private final ISettingService settingService;

    public SettingController(ISettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/settings/{id}")
    public ResponseEntity<SettingResponse> getById(@PathVariable(name = "id") long id) {
        Optional<Setting> optionalSetting = this.settingService.findById(id);
        return optionalSetting.map(setting -> ResponseEntity.ok(new SettingResponse(setting.getId(), setting.getName().getName(), setting.getValue()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingListResponse> getAll() {
        Optional<Set<Setting>> optionalSettings = this.settingService.findAll();
        if (optionalSettings.isPresent()) {
            SettingListResponse settingListResponse = new SettingListResponse();
            for (Setting setting : optionalSettings.get()) {
                settingListResponse.add(new SettingResponse(setting.getId(), setting.getName().getName(), setting.getValue()));
            }
            return ResponseEntity.ok(settingListResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/settings")
    public ResponseEntity<SettingResponse> save(@RequestBody SettingRequest settingRequest) {
        Optional<SettingNames> settingName = Arrays.stream(SettingNames.values()).filter(s -> s.getName().equals(settingRequest.getName())).findFirst();
        if (settingName.isPresent()) {
            Optional<Setting> optionalSetting = this.settingService.save(settingName.get(), settingRequest.getValue());
            if (optionalSetting.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body((new SettingResponse(optionalSetting.get().getId(), optionalSetting.get().getName().getName(), optionalSetting.get().getValue())));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/settings")
    public ResponseEntity<SettingResponse> update(@RequestBody SettingRequest settingRequest) {
        Optional<Setting> optionalSetting = this.settingService.findById(settingRequest.getId());
        if (optionalSetting.isPresent()) {
            Optional<Setting> optionalUpdatedSetting = this.settingService.update(optionalSetting.get().getId(), optionalSetting.get().getValue());
            if (optionalUpdatedSetting.isPresent()) {
                return ResponseEntity.accepted().body(new SettingResponse(optionalUpdatedSetting.get().getId(), optionalUpdatedSetting.get().getName().getName(), optionalUpdatedSetting.get().getValue()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
