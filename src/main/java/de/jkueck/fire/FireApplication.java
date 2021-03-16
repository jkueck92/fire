package de.jkueck.fire;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class FireApplication implements ApplicationRunner {

    private final Environment environment;

    public FireApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(FireApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("property diveraToken: (" + this.environment.getProperty(ApplicationProperties.PROP_DIVERA_TOKEN) + ")");
        log.info("property botToken: (" + this.environment.getProperty(ApplicationProperties.PROP_BOT_TOKEN) + ")");
        log.info("property chatIds: (" + this.environment.getProperty(ApplicationProperties.PROP_CHAT_IDS) + ")");
        log.info("property comPort: (" + this.environment.getProperty(ApplicationProperties.PROP_COM_PORT) + ")");
    }

}
