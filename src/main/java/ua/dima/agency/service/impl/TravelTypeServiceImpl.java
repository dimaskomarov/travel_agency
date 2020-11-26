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
        Optional<TravelType> travelTypeOptional = travelTypeRepository.create(ParserUtil.parse(travelTypeDTO));

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("{} hasn't been created.", travelTypeDTO);
            throw new SQLException(String.format("%s hasn't been created.", travelTypeDTO));
        }
        return ParserUtil.parse(travelTypeOptional.get());
    }

    @Override
    public TravelTypeDto get(Long id) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.get(id);

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("TravelType with id {} hasn't been found in the database.", id);
            throw new NoDataException(String.format("TravelType with id %d hasn't been found in the database.", id));
        }
        return ParserUtil.parse(travelTypeOptional.get());
    }

    @Override
    public List<TravelTypeDto> getAll() {
        List<TravelType> travelTypes = travelTypeRepository.getAll();

        if(travelTypes.isEmpty()) {
            LOGGER.error("Any travelType hasn't been found in the database.");
            throw new NoDataException("Any travelType hasn't been found in the database.");
        }
        return travelTypes.stream().map(ParserUtil::parse).collect(Collectors.toList());
    }

    @Override
    public TravelTypeDto update(Long id, TravelTypeDto travelTypeDTO) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.update(id, ParserUtil.parse(travelTypeDTO));

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("{} hasn't been updated.", travelTypeDTO);
            throw new SQLException(String.format("%s hasn't been updated.", travelTypeDTO));
        }
        return ParserUtil.parse(travelTypeOptional.get());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tourRepository.deleteByTourTypeId(id);
        travelTypeRepository.delete(id);
    }
}
