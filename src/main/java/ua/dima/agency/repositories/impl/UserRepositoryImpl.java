package ua.dima.agency.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.dima.agency.domain.User;
import ua.dima.agency.repositories.UserRepository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final RowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE login = ?", ROW_MAPPER, login));
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
