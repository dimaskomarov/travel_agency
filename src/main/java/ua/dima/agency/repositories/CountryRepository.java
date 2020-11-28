package ua.dima.agency.repositories;

import ua.dima.agency.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    List<Country> getAll();
    Optional<Country> get(Long id);
    Optional<Country> get(String name);
    Optional<Country> create(Country country);
    List<Country> createAll(List<Country> countries);
    List<Country> createAllByName(List<String> countryNames);
    Optional<Country> update(Long id, Country country);
    void delete(Long id);
}
