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
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    private CountryRepository countryRepository;
    private CountryTourRepository countryTourRepository;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountryTourRepository countryTourRepository) {
        this.countryRepository = countryRepository;
        this.countryTourRepository = countryTourRepository;
    }

    @Override
    public CountryDto create(CountryDto countryDto) {
        checkForExistence(countryDto);

        Optional<Country> createdCountry = countryRepository.create(ParserUtil.parse(countryDto));
        if(createdCountry.isPresent()) {
            return ParserUtil.parse(createdCountry.get());
        }
        LOGGER.warn("{} wasn't created.", countryDto);
        throw new SQLException(String.format("%s wasn't created.", countryDto));
    }

    private void checkForExistence(CountryDto countryDto) {
        Long countryDtoId;
        if((countryDtoId = isAlreadyExists(countryDto)) > 0) {
            countryDto.setId(countryDtoId);
            LOGGER.warn("{} already exists.", countryDto);
            throw new ExtraDataException(String.format("%s already exists.", countryDto));
        }
    }

    private Long isAlreadyExists(CountryDto countryDto) {
        List<Country> countries = countryRepository.getAll();
        Optional<Country> existedCountry = countries.stream().filter(country -> country.getName().equals(countryDto.getName())).findFirst();
        return existedCountry.isPresent() ? existedCountry.get().getId() : -1L;
    }

    @Override
    public CountryDto get(Long id) {
        Optional<Country> country = countryRepository.get(id);
        if(country.isPresent()) {
            return ParserUtil.parse(country.get());
        }
        LOGGER.warn("Country with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Country with id=%d doesn't exist.", id));
    }

    @Override
    public List<CountryDto> getAll() {
        List<Country> countries = countryRepository.getAll();
        if(!countries.isEmpty()) {
            return countries.stream().map(ParserUtil::parse).collect(Collectors.toList());
        }
        LOGGER.warn("There aren't any countries in database.");
        throw new NoDataException("There aren't any countries in database.");
    }

    @Override
    public CountryDto update(Long id, CountryDto countryDto) {
        Optional<Country> updatedCountry = countryRepository.update(id, ParserUtil.parse(countryDto));
        if(updatedCountry.isPresent()) {
            return ParserUtil.parse(updatedCountry.get());
        }
        LOGGER.warn("{} wasn't updated.", updatedCountry);
        throw new SQLException(String.format("%s wasn't updated.", updatedCountry));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            countryTourRepository.deleteByCountryId(id);
            countryRepository.delete(id);
        } catch(RuntimeException e) {
            LOGGER.warn("Country with id={} wasn't deleted.", id);
            throw new SQLException(String.format("Country with id=%d wasn't deleted.", id));
        }
    }
}
