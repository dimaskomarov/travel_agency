package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.CountryTour;
import ua.dima.agency.repositories.CountryTourRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class CountryTourRepositoryImpl implements CountryTourRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryTourRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public CountryTourRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<CountryTour>> getAll() {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM countries_tours", new BeanPropertyRowMapper<>(CountryTour.class)));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<CountryTour>> getAllByCountryId(Long countryId) {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM countries_tours WHERE country_id = ?", new BeanPropertyRowMapper<>(CountryTour.class), countryId));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<CountryTour>> getAllByTourId(Long tourId) {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM countries_tours WHERE tour_id = ?", new BeanPropertyRowMapper<>(CountryTour.class), tourId));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
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
    public void delete(CountryTour countryTour) {
        try {
            jdbcTemplate.update("DELETE FROM countries_tours WHERE tour_id=? AND country_id=?", countryTour.getTourId(), countryTour.getCountryId());
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
