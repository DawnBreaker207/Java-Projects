package org.dawn.backend.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatabaseConfig {
    @Getter
    private static HikariDataSource dataSource;

    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.pass}")
    private String password;
    @Value("${db.driver}")
    private String driver;

    @Bean
    public DataSource configDataSource() {
        if (dataSource == null) {
            log.info("Initializing HikariCP Connection Pool...");
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);

            // Config Oracle
            config.setDriverClassName(driver);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setValidationTimeout(5000);
            config.setInitializationFailTimeout(120_000);
            config.setConnectionTestQuery("SELECT 1 FROM DUAL");

            dataSource = new HikariDataSource(config);
            log.info("Hikari initialized successfully");
        }

        return dataSource;
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
            log.info("Database Connection Pool closed.");
        }
    }
}
