package ua.dima.agency.repositories;

import ua.dima.agency.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    List<Country> getAll();
    Optional<Country> get(Long id);
    Optional<Country> create(Country country);
    List<Country> createAll(List<Country> countries);
    Optional<Country> update(Long id, Country country);
    void delete(Long id);
}
