package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.dima.agency.dto.Company;
import ua.dima.agency.dto.Country;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl implements CountryRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public CountryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Country>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Country> getOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Country> create(Country country) {
        return Optional.empty();
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

//    @Override
//    public Optional<List<Company>> getAll() {
//        try {
//            return Optional.of(jdbcTemplate.query("SELECT * FROM companies", new BeanPropertyRowMapper<>(Company.class)));
//        } catch(DataAccessException e) {
//            LOGGER.debug("Method getAll has been failed", e);
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<Company> getOne(Long id) {
//        try {
//            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM companies WHERE id = ?", new BeanPropertyRowMapper<>(Company.class), id));
//        } catch(DataAccessException e) {
//            LOGGER.debug("Method getOne has been failed", e);
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<Company> create(Company company) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO companies(name, address, age) VALUES(?, ?, ?)", new String[] {"id"});
//            statement.setString(1, company.getName());
//            statement.setString(2, company.getAddress());
//            statement.setInt(3, company.getAge());
//            return statement;
//        }, keyHolder);
//        long id = Optional.of(keyHolder.getKey().longValue()).orElse(0L);
//        return getOne(id);
//    }
//
//    @Override
//    public Optional<Company> update(Long id, Company company) {
//        try {
//            jdbcTemplate.update("UPDATE companies SET name=?, address=?, age=? WHERE id=?", company.getName(), company.getAddress(), company.getAge(), id);
//            return getOne(id);
//        } catch (DataAccessException e) {
//            LOGGER.debug("Method update has been failed", e);
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        try {
//            jdbcTemplate.update("DELETE FROM companies WHERE id = ?", id);
//        } catch (DataAccessException e) {
//            LOGGER.debug("Method delete has been failed", e);
//        }
//    }
}
