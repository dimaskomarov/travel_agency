package ua.dima.agency.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.Company;
import ua.dima.agency.repositories.CompanyRepository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRepositoryImpl.class);
    private static final BeanPropertyRowMapper<Company> COMPANY_MAPPER =  new BeanPropertyRowMapper<>(Company.class);
            
    private final JdbcTemplate jdbcTemplate;

    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Company> getAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM companies", COMPANY_MAPPER);
        } catch(DataAccessException e) {
            LOGGER.debug("Method getAll has been failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Company> get(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM companies WHERE id = ?", COMPANY_MAPPER, id));
        } catch(DataAccessException e) {
            LOGGER.debug("Method getOne has been failed", e);
            return Optional.empty();
        }
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
        try {
            jdbcTemplate.update("UPDATE companies SET name=?, address=?, age=? WHERE id=?", company.getName(), company.getAddress(), company.getAge(), id);
            return get(id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method update has been failed", e);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM companies WHERE id = ?", id);
        } catch (DataAccessException e) {
            LOGGER.debug("Method delete has been failed", e);
        }
    }
}
