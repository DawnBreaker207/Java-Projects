package org.dawn.backend.config.database;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class FlywayConfig {

    @Value("${flyway.locations}")
    private String location;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        log.info("Starting Flyway Migration...");
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource)
                .locations(location)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load();

        try {
            flyway.migrate();

        } catch (Exception e) {
            log.warn("Flyway migration failed, attempting repair...", e);
            flyway.repair();
        }
        log.info("Flyway Migration completed successfully");
        return flyway;
    }
}
