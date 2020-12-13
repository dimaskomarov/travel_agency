package ua.dima.agency.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.stream.Collectors;

@Component
public class CountryRepositoryImpl implements CountryRepository {
    private static final BeanPropertyRowMapper<Country> COUNTRY_MAPPER =  new BeanPropertyRowMapper<>(Country.class);

    private final JdbcTemplate jdbcTemplate;

    public CountryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> getAll() {
        return jdbcTemplate.query("SELECT * FROM countries", COUNTRY_MAPPER);
    }

    @Override
    public Optional<Country> get(Long id) {
        try {
            Country country = jdbcTemplate.queryForObject("SELECT * FROM countries WHERE id = ?", COUNTRY_MAPPER, id);
            return Optional.ofNullable(country);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Country> get(String name) {
        try {
            Country country = jdbcTemplate.queryForObject("SELECT * FROM countries WHERE name=?", COUNTRY_MAPPER, name);
            return Optional.ofNullable(country);
        } catch(EmptyResultDataAccessException e) {
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
        return get(id);
    }

    @Override
    public List<Country> createAll(List<Country> countries) {
        return countries.stream().map(this::create)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Country> createAllByName(List<String> countryNames) {
        return countryNames.stream().map(name -> create(Country.create().withName(name).build()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        try {
            jdbcTemplate.update("UPDATE countries SET name=? WHERE id=?", country.getName(), id);
            return get(id);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM countries WHERE id = ?", id);
    }
}
