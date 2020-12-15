package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Country;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.service.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    private final CountryTourRepository countryTourRepository;
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryTourRepository countryTourRepository,
                              CountryRepository countryRepository) {
        this.countryTourRepository = countryTourRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto get(Long id) {
        Optional<Country> country = countryRepository.get(id);
        if(country.isPresent()) {
            return CountryDto.parse(country.get());
        }
        LOGGER.debug("Country with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Country with id=%d doesn't exist.", id));
    }

    @Override
    public CountryDto get(String name) {
        Optional<Country> country = countryRepository.get(name);
        if(country.isPresent()) {
            return CountryDto.parse(country.get());
        }
        LOGGER.debug("Country {} doesn't exist.", name);
        throw new NoDataException(String.format("Country with %s doesn't exist.", name));
    }

    @Override
    public List<CountryDto> getAll() {
        List<Country> countries = countryRepository.getAll();
        if(!countries.isEmpty()) {
            return countries.stream().map(CountryDto::parse).collect(Collectors.toList());
        }
        LOGGER.debug("There aren't any countries in database.");
        throw new NoDataException("There aren't any countries in database.");
    }

    @Override
    public CountryDto create(CountryDto countryDto) {
        checkIfCountryNew(countryDto.getName());

        Optional<Country> createdCountry = countryRepository.create(Country.parse(countryDto));
        if(createdCountry.isPresent()) {
            return CountryDto.parse(createdCountry.get());
        }
        LOGGER.debug("{} wasn't created.", countryDto);
        throw new SQLException(String.format("%s wasn't created.", countryDto));
    }

    @Override
    public CountryDto update(Long id, CountryDto countryDto) {
        checkIfCountryNew(countryDto.getName());

        Optional<Country> updatedCountry = countryRepository.update(id, Country.parse(countryDto));
        if(updatedCountry.isPresent()) {
            return CountryDto.parse(updatedCountry.get());
        }
        LOGGER.debug("{} wasn't updated.", updatedCountry);
        throw new SQLException(String.format("%s wasn't updated.", updatedCountry));
    }

    private void checkIfCountryNew(String name) {
        countryRepository.get(name).ifPresent(country -> {
            LOGGER.debug("{} already exists.", country);
            throw new ExtraDataException(String.format("%s already exists.", country));
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            checkIfCountryExist(id);
            countryTourRepository.deleteByCountryId(id);
            countryRepository.delete(id);
        } catch(SQLException e) {
            LOGGER.debug("Country with id={} wasn't deleted.", id);
            throw new SQLException(String.format("Country with id=%d wasn't deleted.", id));
        }
    }

    private void checkIfCountryExist(Long id) {
        get(id);
    }
}
