package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Country;
import ua.dima.agency.domain.CountryTour;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.ParseException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.TourService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);
    private static final String LOG_PARSING_ERROR = "The parsing of {} with id {} has been failed.";
    private static final String MSG_PARSING_ERROR = "The parsing of %s with id %d has been failed.";
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;
    private CountryRepository countryRepository;
    private TourRepository tourRepository;

    public TourServiceImpl(CountryTourRepository countryTourRepository,
                           TravelTypeRepository travelTypeRepository,
                           CountryRepository countryRepository,
                           TourRepository tourRepository) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.countryRepository = countryRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    @Transactional
    public TourDto create(TourDto tourDTO) {
        Optional<Tour> tourOptional = tourRepository.create(parse(tourDTO));

        if(tourOptional.isEmpty()) {
            LOGGER.error("{} hasn't been created.", tourDTO);
            throw new SQLException(String.format("%s hasn't been created.", tourDTO));
        }
        return parse(tourOptional.get());
    }

    @Override
    public TourDto get(Long id) {
        Optional<Tour> tourOptional = tourRepository.getOne(id);

        if(tourOptional.isEmpty()) {
            LOGGER.error("Tour with id {} hasn't been found in the database.", id);
            throw new NoDataException(String.format("Tour with id %s hasn't been found in the database.", id));
        }
        return parse(tourOptional.get());
    }

    @Override
    public List<TourDto> getAll() {
        List<Tour> tours = tourRepository.getAll();

        if(tours.isEmpty()) {
            LOGGER.error("Any tour hasn't been found in the database.");
            throw new NoDataException("Any tour hasn't been found in the database.");
        }
        return tours.stream().map(tour -> parse(tour))
                .collect(Collectors.toList());
    }

    @Override
    public TourDto update(Long id, TourDto tourDTO) {
        Optional<Tour> tourOptional = tourRepository.update(id, parse(tourDTO));

        if(tourOptional.isEmpty()) {
            LOGGER.error("{} hasn't been updated.", tourDTO);
            throw new SQLException(String.format("{} hasn't been updated.", tourDTO));
        }
        return parse(tourOptional.get());
    }

    @Override
    public void delete(Long id) {
        tourRepository.delete(id);
    }

    private Tour parse(TourDto tourDTO) {
        Long companyId = null;
        Long travelTypeId = null;
        try {
            Optional<Tour> tourOptional = tourRepository.getOne(tourDTO.getId());
            companyId = tourOptional.get().getCompanyId();
            travelTypeId = tourOptional.get().getTravelTypeId();
        } catch(Exception e) {
            LOGGER.error(LOG_PARSING_ERROR, "TourDto", tourDTO.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "TourDto", tourDTO.getId()));
        }

        return Tour.parse(tourDTO, companyId, travelTypeId);
    }

    private TourDto parse(Tour tour) {
        TravelTypeDto travelTypeDto = null;
        List<CountryDto> countriesDto = null;
        try {
            Optional<TravelType> travelTypeOptional = travelTypeRepository.getOne(tour.getTravelTypeId());
            travelTypeDto = TravelTypeDto.parse(travelTypeOptional.get());

            List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tour.getId());
            List<Country> counties = countryTours.stream().map(countryTour -> countryRepository.getOne(countryTour.getCountryId()).get()).collect(Collectors.toList());
            countriesDto = counties.stream().map(CountryDto::parse).collect(Collectors.toList());
        } catch(Exception e) {
            LOGGER.error(LOG_PARSING_ERROR, "Tour", tour.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Tour", tour.getId()));
        }

        return TourDto.parse(tour, travelTypeDto, countriesDto);
    }
}
