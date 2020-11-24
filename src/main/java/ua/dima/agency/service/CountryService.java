package ua.dima.agency.service;

import ua.dima.agency.dto.CountryDto;

public interface CountryService {
    CountryDto create(CountryDto countryDTO);
    CountryDto get(Long id);
    CountryDto update(Long id, CountryDto countryDTO);
    void delete(Long id);
}
