package ua.dima.agency.repositories.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.Company;
import ua.dima.agency.repositories.CompanyRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {
    private static final BeanPropertyRowMapper<Company> COMPANY_MAPPER =  new BeanPropertyRowMapper<>(Company.class);
            
    private final JdbcTemplate jdbcTemplate;

    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Company> getAll() {
        return jdbcTemplate.query("SELECT * FROM companies", COMPANY_MAPPER);
    }

    @Override
    public Optional<Company> get(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM companies WHERE id = ?", COMPANY_MAPPER, id));
    }

    @Override
    public Optional<Company> create(Company company) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO companies(name, address, age) VALUES(?, ?, ?)", new String[] {"id"});
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setInt(3, company.getAge());
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
    public Optional<Company> update(Long id, Company company) {
        jdbcTemplate.update("UPDATE companies SET name=?, address=?, age=? WHERE id=?", company.getName(), company.getAddress(), company.getAge(), id);
        return get(id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM companies WHERE id = ?", id);
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
