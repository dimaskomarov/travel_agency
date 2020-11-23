package ua.dima.agency.repositories;

import ua.dima.agency.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    List<Country> getAll();
    Optional<Country> getOne(Long id);
    Optional<Country> create(Country country);
    Optional<Country> update(Long id, Country country);
    void delete(Long id);
}
