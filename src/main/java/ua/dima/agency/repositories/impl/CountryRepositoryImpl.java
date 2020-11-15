package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.Country;
import ua.dima.agency.repositories.CountryRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class CountryRepositoryImpl implements CountryRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public CountryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Country>> getAll() {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM countries", new BeanPropertyRowMapper<>(Country.class)));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Country> getOne(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM countries WHERE id = ?", new BeanPropertyRowMapper<>(Country.class), id));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Country> create(Country country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO countries(name) VALUES(?)", new String[] {"id"});
            statement.setString(1, country.getName());
            return statement;
        }, keyHolder);
        long id = 0;
        Optional<Number> key = Optional.ofNullable(keyHolder.getKey());
        if(key.isPresent()) {
            id = key.get().longValue();
        }
        return getOne(id);
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        try {
            jdbcTemplate.update("UPDATE countries SET name=? WHERE id=?", country.getName(), id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
        return getOne(id);
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM countries WHERE id = ?", id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
