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
    public Optional<CountryTour> getOneByCountryId(Long countryId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM countries_tours WHERE country_id = ?", new BeanPropertyRowMapper<>(CountryTour.class), countryId));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CountryTour> getOneByTourId(Long tourId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM countries_tours WHERE tour_id = ?", new BeanPropertyRowMapper<>(CountryTour.class), tourId));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CountryTour> create(CountryTour countryTour) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO countries_tours(country_id, tour_id) VALUES(?, ?)", new String[] {"id"});
            statement.setLong(1, countryTour.getCountryId());
            statement.setLong(2, countryTour.getTourId());
            return statement;
        });
        return Optional.of(countryTour);
    }

    @Override
    public Optional<CountryTour> updateByCountryId(Long countryId, CountryTour countryTour) {
        try {
            jdbcTemplate.update("UPDATE countries_tours SET country_id=? WHERE tour_id=?", countryTour.getCountryId(), countryTour.getTourId());
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
        return getOneByCountryId(countryId);
    }

    @Override
    public Optional<CountryTour> updateByTourId(Long tourId, CountryTour countryTour) {
        try {
            jdbcTemplate.update("UPDATE countries_tours SET tour_id=? WHERE country_id=?", countryTour.getTourId(), countryTour.getCountryId());
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
        return getOneByTourId(tourId);
    }

    @Override
    public void deleteByCountryId(Long countryId) {
        try {
            jdbcTemplate.update("DELETE FROM countries_tours WHERE country_id = ?", countryId);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }

    @Override
    public void deleteByTourId(Long tourId) {
        try {
            jdbcTemplate.update("DELETE FROM countries_tours WHERE tour_id = ?", tourId);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
