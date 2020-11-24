package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.dima.agency.domain.Country;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.service.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    private CountryRepository countryRepository;


    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto create(CountryDto countryDto) {
        Optional<Country> country = countryRepository.create(Country.parse(countryDto));

        if(country.isEmpty()) {
            LOGGER.error("{} hasn't been created.", countryDto);
            throw new SQLException(String.format("%s hasn't been created.", countryDto));
        }
        return CountryDto.parse(country.get());
    }

    @Override
    public CountryDto get(Long id) {
        Optional<Country> country = countryRepository.getOne(id);

        if(country.isEmpty()) {
            LOGGER.error("Country with id {} hasn't been found in the database.", id);
            throw new NoDataException(String.format("Country with id %s hasn't been found in the database.", id));
        }
        return CountryDto.parse(country.get());
    }

    @Override
    public List<CountryDto> getAll() {
        List<Country> countries = countryRepository.getAll();

        if(countries.isEmpty()) {
            LOGGER.error("Nothing has been found in the database.");
            throw new NoDataException("Nothing has been found in the database.");
        }
        return countries.stream().map(CountryDto::parse)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto update(Long id, CountryDto countryDTO) {
        Optional<Country> country = countryRepository.update(id, Country.parse(countryDTO));

        if(country.isEmpty()) {
            LOGGER.error("{} hasn't been updated.", countryDTO);
            throw new SQLException(String.format("{} hasn't been updated.", countryDTO));
        }
        return CountryDto.parse(country.get());
    }

    @Override
    public void delete(Long id) {
        countryRepository.delete(id);
    }
}
