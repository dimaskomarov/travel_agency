package ua.dima.agency.repositories;

import ua.dima.agency.dto.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    Optional<List<Country>> getAll();
    Optional<Country> getOne(Long id);
    Optional<Country> create(Country country);
    Optional<Country> update(Long id, Country country);
    void delete(Long id);
}
