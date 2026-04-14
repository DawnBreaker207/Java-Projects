package org.dawn.backend;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

@Slf4j
public class FlywayConfig {

    public static void migrate() {
        log.info("String Flyway Migration...");
        Flyway flyway = Flyway
                .configure()
                .dataSource(DatabaseConfig.getDataSource())
                .locations(AppConfig.get("flyway.locations"))
                .baselineOnMigrate(true).load();

        flyway.migrate();

        log.info("Flyway Migration completed successfully");
    }
}
