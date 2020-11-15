package ua.dima.agency.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.dima.agency.events.ApplicationReadyEvent;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.repositories.impl.CompanyRepositoryImpl;
import ua.dima.agency.repositories.impl.CountryRepositoryImpl;
import ua.dima.agency.repositories.impl.TourRepositoryImpl;
import ua.dima.agency.repositories.impl.TravelTypeRepositoryImpl;
import ua.dima.agency.service.AgencyService;
import ua.dima.agency.service.impl.AgencyServiceImpl;

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

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CompanyRepository companyRepository(JdbcTemplate jdbcTemplate) {
        return new CompanyRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public TourRepository tourRepository(JdbcTemplate jdbcTemplate) {
        return new TourRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public CountryRepository countryRepository(JdbcTemplate jdbcTemplate) {
        return new CountryRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public TravelTypeRepository travelTypeRepository(JdbcTemplate jdbcTemplate) {
        return new TravelTypeRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public AgencyService agencyService(CompanyRepository companyRepository,
                                       TourRepository tourRepository,
                                       CountryRepository countryRepository,
                                       TravelTypeRepository travelTypeRepository) {
        return new AgencyServiceImpl(companyRepository, tourRepository, countryRepository, travelTypeRepository);
    }
}
