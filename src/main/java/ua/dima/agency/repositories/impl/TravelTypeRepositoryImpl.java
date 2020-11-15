package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.repositories.TravelTypeRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class TravelTypeRepositoryImpl implements TravelTypeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelTypeRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public TravelTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<TravelType>> getAll() {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM travel_types", new BeanPropertyRowMapper<>(TravelType.class)));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<TravelType> getOne(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE id = ?", new BeanPropertyRowMapper<>(TravelType.class), id));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<TravelType> create(TravelType travelType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO travel_types(type) VALUES(?)", new String[] {"id"});
            statement.setString(1, travelType.getType());
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
    public Optional<TravelType> update(Long id, TravelType travelType) {
        try {
            jdbcTemplate.update("UPDATE travel_types SET type=? WHERE id=?", travelType.getType(), id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
        return getOne(id);
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM travel_types WHERE id = ?", id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
