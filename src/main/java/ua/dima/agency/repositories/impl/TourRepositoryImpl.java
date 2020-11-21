package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.repositories.TourRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class TourRepositoryImpl implements TourRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public TourRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Tour>> getAll() {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM tours", new BeanPropertyRowMapper<>(Tour.class)));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tour> getOne(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM tours WHERE id = ?", new BeanPropertyRowMapper<>(Tour.class), id));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tour> create(Tour tour) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tours(price, amount_day, date_departure, company_id, travel_type_id) VALUES(?, ?, ?::timestamp, ?, ?)", new String[] {"id"});
            statement.setDouble(1, tour.getPrice());
            statement.setInt(2, tour.getAmountDay());
            statement.setString(3, tour.getDateDeparture().toString());
            statement.setLong(4, tour.getCompanyId());
            statement.setLong(5, tour.getTravelTypeId());
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
    public Optional<Tour> update(Long id, Tour tour) {
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement("UPDATE tours SET price=?, amount_day=?, date_departure=?::timestamp, company_id=?, travel_type_id=? WHERE id=?");
                statement.setDouble(1, tour.getPrice());
                statement.setInt(2, tour.getAmountDay());
                statement.setString(3, tour.getDateDeparture().toString());
                statement.setLong(4, tour.getCompanyId());
                statement.setLong(5, tour.getTravelTypeId());
                statement.setLong(6, id);
                return statement;
            });
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
        return getOne(id);
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM tours WHERE id = ?", id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
