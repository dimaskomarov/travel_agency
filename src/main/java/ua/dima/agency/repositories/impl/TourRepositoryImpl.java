package ua.dima.agency.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.repositories.TourRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class TourRepositoryImpl implements TourRepository {
    private static final BeanPropertyRowMapper<Tour> TOUR_MAPPER =  new BeanPropertyRowMapper<>(Tour.class);

    private final JdbcTemplate jdbcTemplate;

    public TourRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tour> getAll() {
        return jdbcTemplate.query("SELECT * FROM tours", TOUR_MAPPER);
    }

    @Override
    public Optional<Tour> get(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM tours WHERE id = ?", TOUR_MAPPER, id));
    }

    @Override
    public List<Tour> getByCompanyId(Long companyId) {
        return jdbcTemplate.query("SELECT * FROM tours WHERE company_id = ?", TOUR_MAPPER, companyId);
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
        return get(id);
    }

    @Override
    public Optional<Tour> update(Long id, Tour tour) {
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
        return get(id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM tours WHERE id = ?", id);
    }

    @Override
    public void deleteByTourTypeId(Long travelTypeId) {
        jdbcTemplate.update("DELETE FROM tours WHERE travel_type_id = ?", travelTypeId);
    }

    @Override
    public void deleteByCompanyId(Long companyId) {
        jdbcTemplate.update("DELETE FROM tours WHERE company_id = ?", companyId);
    }

//    @Override
//    public Optional<TravelType> get(Long id) {
//        try {
//            TravelType travelType = jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE id = ?", TRAVEL_TYPE_MAPPER, id);
//            return Optional.ofNullable(travelType);
//        } catch(EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<TravelType> getByName(String type) {
//        try {
//            TravelType travelType = jdbcTemplate.queryForObject("SELECT * FROM travel_types WHERE type=?", TRAVEL_TYPE_MAPPER, type);
//            return Optional.ofNullable(travelType);
//        } catch(EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<TravelType> create(TravelType travelType) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO travel_types(type) VALUES(?)", new String[] {"id"});
//            statement.setString(1, travelType.getType());
//            return statement;
//        }, keyHolder);
//        long id = 0;
//        Optional<Number> key = Optional.ofNullable(keyHolder.getKey());
//        if(key.isPresent()) {
//            id = key.get().longValue();
//        }
//        return get(id);
//    }
//
//    @Override
//    public Optional<TravelType> update(Long id, TravelType travelType) {
//        try {
//            jdbcTemplate.update("UPDATE travel_types SET type=? WHERE id=?", travelType.getType(), id);
//            return get(id);
//        } catch(EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        jdbcTemplate.update("DELETE FROM travel_types WHERE id = ?", id);
//    }
}
