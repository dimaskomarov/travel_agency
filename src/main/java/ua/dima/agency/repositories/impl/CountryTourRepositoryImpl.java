package ua.dima.agency.repositories.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.CountryTour;
import ua.dima.agency.repositories.CountryTourRepository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

@Component
public class CountryTourRepositoryImpl implements CountryTourRepository {
    private static final BeanPropertyRowMapper<CountryTour> COUNTRY_TOUR_MAPPER =  new BeanPropertyRowMapper<>(CountryTour.class);
    private final JdbcTemplate jdbcTemplate;

    public CountryTourRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CountryTour> getAll() {
        return jdbcTemplate.query("SELECT * FROM countries_tours", COUNTRY_TOUR_MAPPER);
    }

    @Override
    public List<CountryTour> getAllByCountryId(Long countryId) {
        return jdbcTemplate.query("SELECT * FROM countries_tours WHERE country_id = ?", COUNTRY_TOUR_MAPPER, countryId);
    }

    @Override
    public List<CountryTour> getAllByTourId(Long tourId) {
        return jdbcTemplate.query("SELECT * FROM countries_tours WHERE tour_id = ?", COUNTRY_TOUR_MAPPER, tourId);
    }

    @Override
    public void create(CountryTour countryTour) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO countries_tours(country_id, tour_id) VALUES(?, ?)");
            statement.setLong(1, countryTour.getCountryId());
            statement.setLong(2, countryTour.getTourId());
            return statement;
        });
    }

    @Override
    public void create(Long tourId, Long countryId) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO countries_tours(country_id, tour_id) VALUES(?, ?)");
            statement.setLong(1, countryId);
            statement.setLong(2, tourId);
            return statement;
        });
    }

    @Override
    public void delete(CountryTour countryTour) {
        jdbcTemplate.update("DELETE FROM countries_tours WHERE tour_id=? AND country_id=?", countryTour.getTourId(), countryTour.getCountryId());
    }

    @Override
    public void deleteByTourId(Long tourId) {
        jdbcTemplate.update("DELETE FROM countries_tours WHERE tour_id=?", tourId);
    }

    @Override
    public void deleteByCountryId(Long countryId) {
        jdbcTemplate.update("DELETE FROM countries_tours WHERE country_id=?", countryId);
    }
}
