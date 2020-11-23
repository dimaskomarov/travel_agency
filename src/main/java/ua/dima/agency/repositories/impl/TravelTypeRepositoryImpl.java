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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TravelTypeRepositoryImpl implements TravelTypeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelTypeRepositoryImpl.class);
    private static final BeanPropertyRowMapper<TravelType> TRAVEL_TYPE_MAPPER =  new BeanPropertyRowMapper<>(TravelType.class); //added

    private final JdbcTemplate jdbcTemplate;

    public TravelTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TravelType> getAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM travel_types", TRAVEL_TYPE_MAPPER);
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TravelType> getOne(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE id = ?", TRAVEL_TYPE_MAPPER, id));
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
