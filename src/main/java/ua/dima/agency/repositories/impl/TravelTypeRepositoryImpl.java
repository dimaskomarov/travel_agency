package ua.dima.agency.repositories.impl;

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
    private static final BeanPropertyRowMapper<TravelType> TRAVEL_TYPE_MAPPER =  new BeanPropertyRowMapper<>(TravelType.class);

    private final JdbcTemplate jdbcTemplate;

    public TravelTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TravelType> getAll() {
        return jdbcTemplate.query("SELECT * FROM travel_types", TRAVEL_TYPE_MAPPER);
    }

    @Override
    public Optional<TravelType> get(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE id = ?", TRAVEL_TYPE_MAPPER, id));
    }

    @Override
    public Optional<TravelType> getByName(String type) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE type=?", TRAVEL_TYPE_MAPPER, type));
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
        return get(id);
    }

    @Override
    public Optional<TravelType> update(Long id, TravelType travelType) {
        jdbcTemplate.update("UPDATE travel_types SET type=? WHERE id=?", travelType.getType(), id);
        return get(id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM travel_types WHERE id = ?", id);
    }
}
