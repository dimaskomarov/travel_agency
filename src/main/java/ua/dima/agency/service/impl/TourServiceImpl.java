package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Company;
import ua.dima.agency.domain.CountryTour;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.service.CountryService;
import ua.dima.agency.service.TourService;
import ua.dima.agency.service.TravelTypeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);
    private final CountryTourRepository countryTourRepository;
    private final CompanyRepository companyRepository;
    private final TourRepository tourRepository;

    private final TravelTypeService travelTypeService;
    private final CountryService countryService;

    public TourServiceImpl(CountryTourRepository countryTourRepository,
                           CompanyRepository companyRepository,
                           TourRepository tourRepository,
                           TravelTypeService travelTypeService,
                           CountryService countryService) {
        this.countryTourRepository = countryTourRepository;
        this.companyRepository = companyRepository;
        this.tourRepository = tourRepository;
        this.travelTypeService = travelTypeService;
        this.countryService = countryService;
    }

    @Override
    public TourDto get(Long companyId, Long tourId) {
        checkIfCompanyExists(companyId);
        checkIfCompanyHasAnyTours(companyId);

        Optional<Tour> tour = tourRepository.get(companyId, tourId);
        if(tour.isPresent()) {
            return buildTourDtoWithTypeAndCountry(tour.get());
        }
        LOGGER.debug("The company with id={} doesn't have the tour with id={}", companyId, tourId);
        throw new NoDataException(String.format("The company with id=%d doesn't have the tour with id=%d", companyId, tourId));
    }

    private void checkIfCompanyHasAnyTours(Long companyId) {
        getAll(companyId);
    }

    @Override
    public List<TourDto> getAll(Long companyId) {
        checkIfCompanyExists(companyId);

        List<Tour> tours = tourRepository.getByCompanyId(companyId);
        if(!tours.isEmpty()) {
            return tours.stream().map(this::buildTourDtoWithTypeAndCountry).collect(Collectors.toList());
        }
        LOGGER.debug("The company with id={} doesn't have any tours.", companyId);
        throw new NoDataException(String.format("The company with id=%d doesn't have any tours.", companyId));
    }

    @Override
    @Transactional
    public TourDto create(Long companyId, TourDto tourDto) {
        checkIfCompanyExists(companyId);

        createMissingCounties(tourDto);
        createMissingTravelType(tourDto);

        Tour tour = buildTourWithTypeAndCountry(tourDto, companyId);
        Optional<Tour> createdTour = tourRepository.create(tour);
        if(createdTour.isPresent()) {
            createMissingCountryTour(tourDto.getCountiesDto(), createdTour.get().getId());
            return buildTourDtoWithTypeAndCountry(createdTour.get());
        }
        LOGGER.debug("{} wasn't created.", tourDto);
        throw new SQLException(String.format("%s wasn't created.", tourDto));
    }

    @Override
    @Transactional
    public TourDto update(TourDto tourDto, Long tourId) {
        checkIfTourExists(tourId);

        createMissingCounties(tourDto);
        createMissingTravelType(tourDto);

        countryTourRepository.deleteByTourId(tourId);
        createMissingCountryTour(tourDto.getCountiesDto(), tourId);

        Optional<Tour> updatedTour = tourRepository.update(tourId, getTourFromDB(tourId));
        if(updatedTour.isPresent()) {
            return buildTourDtoWithTypeAndCountry(updatedTour.get());
        }
        LOGGER.debug("{} wasn't updated.", tourDto);
        throw new SQLException(String.format("%s wasn't updated.", tourDto));
    }

    private void checkIfTourExists(Long tourId) {
        Optional<Tour> tour = tourRepository.get(tourId);

        if(tour.isEmpty()) {
            LOGGER.debug("The tour with id={} doesn't exist", tourId);
            throw new NoDataException(String.format("The tour with id=%d doesn't exist", tourId));
        }
    }

    private void createMissingCounties(TourDto tourDto) {
        tourDto.getCountiesDto().forEach(countryDto -> {
            try {
                countryService.create(countryDto);
            } catch(ExtraDataException e) {
                LOGGER.debug(e.getMessage());
            }
        });
    }

    private void createMissingTravelType(TourDto tourDto) {
        try {
            travelTypeService.create(tourDto.getTravelTypeDto());
        } catch(ExtraDataException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    public TourDto buildTourDtoWithTypeAndCountry(Tour tour) {
        TravelTypeDto travelTypeDto = travelTypeService.get(tour.getTravelTypeId());
        List<CountryDto> countriesDto = getCountriesDto(tour.getId());

        return TourDto.parse(tour, travelTypeDto, countriesDto);
    }

    private List<CountryDto> getCountriesDto(Long tourId) {
        List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tourId);
        List<Long> countryIds = countryTours.stream().map(CountryTour::getCountryId).collect(Collectors.toList());
        return countryIds.stream().map(countryService::get).collect(Collectors.toList());
    }

    private Tour buildTourWithTypeAndCountry(TourDto tourDTO, Long companyId) {
        String typeFromTour = tourDTO.getTravelTypeDto().getType();
        TravelTypeDto receivedTravelType = travelTypeService.get(typeFromTour);

        return Tour.parse(tourDTO, companyId, receivedTravelType.getId());
    }

    private Tour getTourFromDB(Long tourId) {
        Optional<Tour> tour = tourRepository.get(tourId);

        return tour.orElseGet(() -> Tour.createTour().build());
    }

    public void createMissingCountryTour(List<CountryDto> countriesDto, Long tourId) {
        List<CountryDto> countriesDtoWithId = countriesDto.stream().map(countryDto -> countryService.get(countryDto.getName())).collect(Collectors.toList());
        try {
            countriesDtoWithId.forEach(countryDto -> countryTourRepository.create(tourId, countryDto.getId()));
        } catch(ExtraDataException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long companyId, Long tourId) {
        checkIfCompanyExists(companyId);
        checkIfCompanyHasTheTour(companyId, tourId);

        try{
            countryTourRepository.deleteByTourId(tourId);
            tourRepository.delete(tourId);
        } catch(SQLException e) {
            LOGGER.debug("Tour with id={} wasn't deleted.", tourId);
            throw new SQLException(String.format("Tour with id=%d wasn't deleted.", tourId));
        }
    }

    private void checkIfCompanyHasTheTour(Long companyId, Long tourId) {
        get(companyId, tourId);
    }

    @Override
    public void delete(Long companyId) {
        checkIfCompanyExists(companyId);

        tourRepository.deleteByCompanyId(companyId);
    }

    private void checkIfCompanyExists(Long companyId) {
        Optional<Company> company = companyRepository.get(companyId);
        if(company.isEmpty()) {
            LOGGER.debug("The company with id={} doesn't exist", companyId);
            throw new NoDataException(String.format("The company with id=%d doesn't exist", companyId));
        }
    }
}
