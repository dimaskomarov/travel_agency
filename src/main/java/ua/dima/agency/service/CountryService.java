package ua.dima.agency.service;

import ua.dima.agency.dto.CountryDTO;

public interface CountryService {
    CountryDTO create(CountryDTO countryDTO);
    CountryDTO get(Long id);
    CountryDTO update(Long id, CountryDTO countryDTO);
    void delete(Long id);
}
