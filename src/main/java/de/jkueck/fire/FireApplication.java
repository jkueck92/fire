package de.jkueck.fire;

import de.jkueck.fire.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FireApplication implements ApplicationRunner {

    private final SystemSettingService systemSettingService;

    public FireApplication(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FireApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        this.systemSettingService.initSettings();
    }

}
