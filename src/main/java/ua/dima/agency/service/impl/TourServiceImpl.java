package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.*;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.ParseException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.*;
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
    private CompanyRepository companyRepository;
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
    @Transactional
    public TourDto create(TourDto tourDTO, Long companyId) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.create(TravelType.parse(tourDTO.getTravelTypeDto()));
        List<Country> countries = tourDTO.getCountiesDto().stream().map(countryDto -> countryRepository.create(Country.parse(countryDto)).get()).collect(Collectors.toList());

        Tour tour = Tour.parse(tourDTO, companyId, travelTypeOptional.get().getId());
        Optional<Tour> tourOptional = tourRepository.create(tour);

//        if(tourOptional.isEmpty()) {
//            LOGGER.error("{} hasn't been created.", tourDTO);
//            throw new SQLException(String.format("%s hasn't been created.", tourDTO));
//        }
        return collect(tourOptional.get());
    }

    @Override
    public TourDto get(Long id) {
        Optional<Tour> tourOptional = tourRepository.get(id);

        if(tourOptional.isEmpty()) {
            LOGGER.error("Tour with id {} hasn't been found in the database.", id);
            throw new NoDataException(String.format("Tour with id %s hasn't been found in the database.", id));
        }
        return collect(tourOptional.get());
    }

    @Override
    public List<TourDto> getAll() {
        List<Tour> tours = tourRepository.getAll();

        if(tours.isEmpty()) {
            LOGGER.error("Any tour hasn't been found in the database.");
            throw new NoDataException("Any tour hasn't been found in the database.");
        }
        return tours.stream().map(tour -> collect(tour))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TourDto update(Long id, TourDto tourDTO) {
//        Optional<Tour> tourOptional = tourRepository.update(id, parse(tourDTO));
//
//        if(tourOptional.isEmpty()) {
//            LOGGER.error("{} hasn't been updated.", tourDTO);
//            throw new SQLException(String.format("{} hasn't been updated.", tourDTO));
//        }
//        return collect(tourOptional.get());
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        countryTourRepository.deleteByTourId(id);
        tourRepository.delete(id);
    }

    private TourDto collect(Tour tour) {
        TravelTypeDto travelTypeDto = null;
        List<CountryDto> countriesDto = null;
        try {
            Optional<TravelType> travelTypeOptional = travelTypeRepository.get(tour.getTravelTypeId());
            travelTypeDto = TravelTypeDto.parse(travelTypeOptional.get());

            List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tour.getId());
            List<Country> counties = countryTours.stream().map(countryTour -> countryRepository.get(countryTour.getCountryId()).get()).collect(Collectors.toList());
            countriesDto = counties.stream().map(CountryDto::parse).collect(Collectors.toList());
        } catch(Exception e) {
            LOGGER.error(LOG_PARSING_ERROR, "Tour", tour.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Tour", tour.getId()));
        }

        return TourDto.parse(tour, travelTypeDto, countriesDto);
    }
}
