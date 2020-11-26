package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Country;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
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
    public TourDto create(TourDto tourDTO, Long companyId) {
        List<Country> countriesFromTour = tourDTO.getCountiesDto().stream().map(ParserUtil::parse).collect(Collectors.toList());
        List<Country> createdCountries = countryRepository.createAll(countriesFromTour);

        createdCountries.forEach(country -> countryTourRepository.create(tourDTO.getId(), country.getId()));

        travelTypeRepository.create(ParserUtil.parse(tourDTO.getTravelTypeDto()));

        Tour tour = ParserUtil.parse(tourDTO, companyId);
        Optional<Tour> createdTour = tourRepository.create(tour);
        if(createdTour.isPresent()) {
            return ParserUtil.parse(createdTour.get());
        }
        LOGGER.warn("{} wasn't created.", tourDTO);
        throw new SQLException(String.format("%s wasn't created.", tourDTO));
    }

    @Override
    public TourDto get(Long id) {
        Optional<Tour> tour = tourRepository.get(id);
        if(tour.isPresent()) {
            return ParserUtil.parse(tour.get());
        }
        LOGGER.warn("Tour with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Tour with id=%d doesn't exist.", id));
    }

    @Override
    public List<TourDto> getAll() {
        List<Tour> tours = tourRepository.getAll();
        if(!tours.isEmpty()) {
            return tours.stream().map(ParserUtil::parse).collect(Collectors.toList());
        }
        LOGGER.warn("There aren't any tours in database.");
        throw new NoDataException("There aren't any tours in database.");
    }

    @Override
    @Transactional
    public TourDto update(TourDto tourDTO, Long countryId) {
        Optional<Tour> updatedTour = tourRepository.update(tourDTO.getId(), ParserUtil.parse(tourDTO, countryId));
        if(updatedTour.isPresent()) {
            return ParserUtil.parse(updatedTour.get());
        }
        LOGGER.warn("{} wasn't updated.", tourDTO);
        throw new SQLException(String.format("%s wasn't updated.", tourDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try{
        countryTourRepository.deleteByTourId(id);
        tourRepository.delete(id);
        } catch(RuntimeException e) {
            LOGGER.warn("Tour with id={} wasn't deleted.", id);
            throw new SQLException(String.format("Tour with id=%d wasn't deleted.", id));
        }
    }
}
