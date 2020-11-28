package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Company;
import ua.dima.agency.domain.Country;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.TourService;
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;
    private CompanyRepository companyRepository;
    private CountryRepository countryRepository;
    private TourRepository tourRepository;

    public TourServiceImpl(CountryTourRepository countryTourRepository,
                           TravelTypeRepository travelTypeRepository,
                           CompanyRepository companyRepository,
                           CountryRepository countryRepository,
                           TourRepository tourRepository) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public TourDto get(Long companyId, Long tourId) {
        checkForExistence(companyId);
        checkIfCompanyHasAnyTours(companyId);

        Optional<Tour> tour = tourRepository.get(companyId, tourId);
        if(tour.isPresent()) {
            return ParserUtil.parse(tour.get());
        }
        LOGGER.warn("The company with id={} doesn't have the tour with id={}", companyId, tourId);
        throw new NoDataException(String.format("The company with id=%d doesn't have the tour with id=%d", companyId, tourId));
    }

    private void checkIfCompanyHasAnyTours(Long companyId) {
        getAll(companyId);
    }

    @Override
    public List<TourDto> getAll(Long companyId) {
        checkForExistence(companyId);

        List<Tour> tours = tourRepository.getByCompanyId(companyId);
        if(!tours.isEmpty()) {
            return ParserUtil.parseTours(tours);
        }
        LOGGER.warn("The company with id={} doesn't have any tours.", companyId);
        throw new NoDataException(String.format("The company with id=%d doesn't have any tours.", companyId));
    }

    @Override
    @Transactional
    public TourDto create(Long companyId, TourDto tourDTO) {
        checkForExistence(companyId);

        createMissingCountries(tourDTO.getCountiesDto());
        createMissingTravelTypes(tourDTO.getTravelTypeDto());

        //tour
        Tour tour = ParserUtil.parse(tourDTO, companyId);
        Optional<Tour> createdTour = tourRepository.create(tour);
        if(createdTour.isPresent()) {
            createMissingCountryTour(tourDTO.getCountiesDto(), createdTour.get().getId());
            return ParserUtil.parse(createdTour.get());
        }
        LOGGER.warn("{} wasn't created.", tourDTO);
        throw new SQLException(String.format("%s wasn't created.", tourDTO));
    }

    @Override
    @Transactional
    public TourDto update(Long companyId, TourDto tourDTO, Long tourId) {
        checkForExistence(companyId);
        checkIfCompanyContentsTour(companyId, tourId);

        createMissingCountries(tourDTO.getCountiesDto());
        createMissingTravelTypes(tourDTO.getTravelTypeDto());

        countryTourRepository.deleteByTourId(tourId);
        createMissingCountryTour(tourDTO.getCountiesDto(), tourId);

        Optional<Tour> updatedTour = tourRepository.update(tourId, ParserUtil.parse(tourDTO, companyId));
        if(updatedTour.isPresent()) {
            return ParserUtil.parse(updatedTour.get());
        }
        LOGGER.warn("{} wasn't updated.", tourDTO);
        throw new SQLException(String.format("%s wasn't updated.", tourDTO));
    }

    private void checkIfCompanyContentsTour(Long companyId, Long tourId) {
        List<Long> toursId = tourRepository.getByCompanyId(companyId).stream().map(Tour::getId).collect(Collectors.toList());
        if(!toursId.contains(tourId)) {
            LOGGER.warn("The company with id={} doesn't have any tour with id={}.", companyId, tourId);
            throw new NoDataException(String.format("The company with id=%d doesn't have any tour with id=%d.", companyId, tourId));
        }
    }

    @Override
    @Transactional
    public void delete(Long companyId, Long tourId) {
        checkForExistence(companyId);

        try{
            countryTourRepository.deleteByTourId(tourId);
            tourRepository.delete(tourId);
        } catch(RuntimeException e) {
            LOGGER.warn("Tour with id={} wasn't deleted.", tourId);
            throw new SQLException(String.format("Tour with id=%d wasn't deleted.", tourId));
        }
    }

    private void createMissingCountryTour(List<CountryDto> countriesDto, Long tourId) {
        countriesDto.stream().map(ParserUtil::parse).forEach(country -> countryTourRepository.create(tourId, country.getId()));
    }

    private void createMissingCountries(List<CountryDto> countriesDto) {
        List<String> countriesNameFromTour = countriesDto.stream().map(CountryDto::getName).collect(Collectors.toList());
        List<String> allCountriesName = countryRepository.getAll().stream().map(Country::getName).collect(Collectors.toList());
        List<String> missingCountries = countriesNameFromTour.stream()
                .filter(countryFromTour -> !allCountriesName.contains(countryFromTour))
                .collect(Collectors.toList());

        if(!missingCountries.isEmpty()) {
            createCountries(missingCountries);
        }
    }

    private void createCountries(List<String> countriesName) {
        countryRepository.createAllByName(countriesName);
    }

    private void createMissingTravelTypes(TravelTypeDto travelTypeDto) {
        List<String> allTravelTypes = travelTypeRepository.getAll().stream().map(TravelType::getType).collect(Collectors.toList());
        String travelTypeFromTour = travelTypeDto.getType();
        if(!allTravelTypes.contains(travelTypeFromTour)) {
            createTravelType(travelTypeFromTour);
        }
    }

    private void createTravelType(String travelType) {
        travelTypeRepository.create(TravelType.createTravelType().withType(travelType).build());
    }

    private void checkForExistence(Long companyId) {
        Optional<Company> company = companyRepository.get(companyId);
        if(company.isEmpty()) {
            LOGGER.warn("The company with id={} doesn't exist", companyId);
            throw new NoDataException(String.format("The company with id=%d doesn't exist", companyId));
        }
    }
}
