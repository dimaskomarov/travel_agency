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
import java.util.Collections;
import java.util.List;

@Component
public class CountryTourRepositoryImpl implements CountryTourRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryTourRepositoryImpl.class);
    private static final BeanPropertyRowMapper<CountryTour> COUNTRY_TOUR_MAPPER =  new BeanPropertyRowMapper<>(CountryTour.class);//added


    private final JdbcTemplate jdbcTemplate;

    public CountryTourRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CountryTour> getAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM countries_tours", COUNTRY_TOUR_MAPPER);
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<CountryTour> getAllByCountryId(Long countryId) {
        try {
            return jdbcTemplate.query("SELECT * FROM countries_tours WHERE country_id = ?", COUNTRY_TOUR_MAPPER, countryId);
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<CountryTour> getAllByTourId(Long tourId) {
        try {
            return jdbcTemplate.query("SELECT * FROM countries_tours WHERE tour_id = ?", COUNTRY_TOUR_MAPPER, tourId);
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Collections.emptyList();
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
