package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.TravelTypeService;
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelTypeServiceImpl implements TravelTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelTypeServiceImpl.class);
    private TravelTypeRepository travelTypeRepository;
    private TourRepository tourRepository;

    public TravelTypeServiceImpl(TravelTypeRepository travelTypeRepository,
                                 TourRepository tourRepository) {
        this.travelTypeRepository = travelTypeRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public TravelTypeDto create(TravelTypeDto travelTypeDTO) {
        Optional<TravelType> createdTravelType = travelTypeRepository.create(ParserUtil.parse(travelTypeDTO));
        if(createdTravelType.isPresent()) {
            return ParserUtil.parse(createdTravelType.get());
        }
        LOGGER.warn("{} wasn't created.", travelTypeDTO);
        throw new SQLException(String.format("%s wasn't created.", travelTypeDTO));
    }

    @Override
    public TravelTypeDto get(Long id) {
        Optional<TravelType> travelType = travelTypeRepository.get(id);
        if(travelType.isPresent()) {
            return ParserUtil.parse(travelType.get());
        }
        LOGGER.warn("TravelType with id={} doesn't exist.", id);
        throw new NoDataException(String.format("TravelType with id=%d doesn't exist.", id));
    }

    @Override
    public List<TravelTypeDto> getAll() {
        List<TravelType> travelTypes = travelTypeRepository.getAll();
        if(!travelTypes.isEmpty()) {
            return travelTypes.stream().map(ParserUtil::parse).collect(Collectors.toList());
        }
        LOGGER.warn("There aren't any travelTypes in database.");
        throw new NoDataException("There aren't any travelTypes in database.");
    }

    @Override
    public TravelTypeDto update(Long id, TravelTypeDto travelTypeDTO) {
        Optional<TravelType> updatedTravelType = travelTypeRepository.update(id, ParserUtil.parse(travelTypeDTO));
        if(updatedTravelType.isPresent()) {
            return ParserUtil.parse(updatedTravelType.get());
        }
        LOGGER.warn("{} wasn't updated.", travelTypeDTO);
        throw new SQLException(String.format("%s wasn't updated.", travelTypeDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            tourRepository.deleteByTourTypeId(id);
            travelTypeRepository.delete(id);
        } catch(RuntimeException e) {
            LOGGER.warn("TravelType with id={} wasn't deleted.", id);
            throw new SQLException(String.format("TravelType with id=%d wasn't deleted.", id));
        }
    }
}
