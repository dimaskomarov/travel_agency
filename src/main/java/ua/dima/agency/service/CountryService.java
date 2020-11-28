package ua.dima.agency.service;

import ua.dima.agency.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto get(Long id);
    List<CountryDto> getAll();
    CountryDto create(CountryDto countryDTO);
    CountryDto update(Long id, CountryDto countryDTO);
    void delete(Long id);
}
