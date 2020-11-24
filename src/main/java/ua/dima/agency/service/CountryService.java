package ua.dima.agency.service;

import ua.dima.agency.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto create(CountryDto countryDTO);
    CountryDto get(Long id);
    List<CountryDto> getAll();
    CountryDto update(Long id, CountryDto countryDTO);
    void delete(Long id);
}
