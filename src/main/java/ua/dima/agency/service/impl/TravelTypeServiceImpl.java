package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.TravelTypeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelTypeServiceImpl implements TravelTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelTypeServiceImpl.class);
    private final CountryTourRepository countryTourRepository;
    private final TravelTypeRepository travelTypeRepository;
    private final TourRepository tourRepository;

    public TravelTypeServiceImpl(CountryTourRepository countryTourRepository,
                                 TravelTypeRepository travelTypeRepository,
                                 TourRepository tourRepository) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public TravelTypeDto get(Long id) {
        Optional<TravelType> travelType = travelTypeRepository.get(id);
        if(travelType.isPresent()) {
            return TravelTypeDto.parse(travelType.get());
        }
        LOGGER.debug("TravelType with id={} doesn't exist.", id);
        throw new NoDataException(String.format("TravelType with id=%d doesn't exist.", id));
    }

    @Override
    public TravelTypeDto get(String type) {
        Optional<TravelType> travelType = travelTypeRepository.get(type);
        if(travelType.isPresent()) {
            return TravelTypeDto.parse(travelType.get());
        }
        LOGGER.debug("TravelType {} doesn't exist.", type);
        throw new NoDataException(String.format("TravelType %s doesn't exist.", type));
    }

    @Override
    public List<TravelTypeDto> getAll() {
        List<TravelType> travelTypes = travelTypeRepository.getAll();
        if(!travelTypes.isEmpty()) {
            return travelTypes.stream().map(TravelTypeDto::parse).collect(Collectors.toList());
        }
        LOGGER.debug("There aren't any travelTypes in database.");
        throw new NoDataException("There aren't any travelTypes in database.");
    }

    @Override
    public TravelTypeDto create(TravelTypeDto travelTypeDto) {
        checkIfTypeNew(travelTypeDto.getType());

        Optional<TravelType> createdTravelType = travelTypeRepository.create(TravelType.parse(travelTypeDto));
        if(createdTravelType.isPresent()) {
            return TravelTypeDto.parse(createdTravelType.get());
        }
        LOGGER.debug("{} wasn't created.", travelTypeDto);
        throw new SQLException(String.format("%s wasn't created.", travelTypeDto));
    }

    @Override
    public TravelTypeDto update(Long id, TravelTypeDto travelTypeDto) {
        checkIfTypeNew(travelTypeDto.getType());

        Optional<TravelType> updatedTravelType = travelTypeRepository.update(id, TravelType.parse(travelTypeDto));
        if(updatedTravelType.isPresent()) {
            return TravelTypeDto.parse(updatedTravelType.get());
        }
        LOGGER.debug("{} wasn't updated.", travelTypeDto);
        throw new SQLException(String.format("%s wasn't updated.", travelTypeDto));
    }

    private void checkIfTypeNew(String type) {
        travelTypeRepository.get(type).ifPresent(travelType -> {
            LOGGER.debug("{} already exists.", travelType);
            throw new ExtraDataException(String.format("%s already exists.", travelType));
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            checkIfTravelTypeExist(id);
            deleteCountryTour(id);
            tourRepository.deleteByTourTypeId(id);
            travelTypeRepository.delete(id);
        } catch(SQLException e) {
            LOGGER.debug("TravelType with id={} wasn't deleted.", id);
            throw new SQLException(String.format("TravelType with id=%d wasn't deleted.", id));
        }
    }

    private void deleteCountryTour(Long travelTypeId) {
        List<Tour> toursByTravelTypeId = tourRepository.getByTravelTypeId(travelTypeId);
        toursByTravelTypeId.forEach(tourId -> countryTourRepository.deleteByTourId(tourId.getId()));
    }

    private void checkIfTravelTypeExist(Long id) {
        get(id);
    }
}
