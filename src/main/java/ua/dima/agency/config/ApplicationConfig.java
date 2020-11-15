package ua.dima.agency.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

@Configuration
public class ApplicationConfig {
    private static final String PROJECT_NAME = "travel_agency";

    @Bean
    public String projectName() {
        return PROJECT_NAME;
    }

    @Bean
    public Instant currentDate() {
        return Instant.now();
    }

    @Bean
    public String stringCurrentDate() {
        return "It is " + currentDate();
    }

    @Bean("yesterday")
    public Supplier<Instant> yesterdayDate() {
        return () -> Instant.now().minus(1L, ChronoUnit.DAYS);
    }

    @Bean("oneMinuteAgo")
    public Supplier<Instant> oneMinuteAgoDate() {
        return () -> Instant.now().minus(1L, ChronoUnit.MINUTES);
    }

    @Bean
    public String internalDate(@Qualifier("yesterday") Supplier<Instant> instantSupplier) {
        return "It is " + instantSupplier.get();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/travel_agency");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");

        return dataSource;
    }
}
