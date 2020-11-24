package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.TravelTypeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelTypeServiceImpl implements TravelTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelTypeServiceImpl.class);
    private TravelTypeRepository travelTypeRepository;

    public TravelTypeServiceImpl(TravelTypeRepository travelTypeRepository) {
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public TravelTypeDto create(TravelTypeDto travelTypeDTO) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.create(TravelType.parse(travelTypeDTO));

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("{} hasn't been created.", travelTypeDTO);
            throw new SQLException(String.format("%s hasn't been created.", travelTypeDTO));
        }
        return TravelTypeDto.parse(travelTypeOptional.get());
    }

    @Override
    public TravelTypeDto get(Long id) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.getOne(id);

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("TravelType with id {} hasn't been found in the database.", id);
            throw new NoDataException(String.format("TravelType with id %d hasn't been found in the database.", id));
        }
        return TravelTypeDto.parse(travelTypeOptional.get());
    }

    @Override
    public List<TravelTypeDto> getAll() {
        List<TravelType> travelTypes = travelTypeRepository.getAll();

        if(travelTypes.isEmpty()) {
            LOGGER.error("Any travelType hasn't been found in the database.");
            throw new NoDataException("Any travelType hasn't been found in the database.");
        }
        return travelTypes.stream().map(TravelTypeDto::parse).collect(Collectors.toList());
    }

    @Override
    public TravelTypeDto update(Long id, TravelTypeDto travelTypeDTO) {
        Optional<TravelType> travelTypeOptional = travelTypeRepository.update(id, TravelType.parse(travelTypeDTO));

        if(travelTypeOptional.isEmpty()) {
            LOGGER.error("{} hasn't been updated.", travelTypeDTO);
            throw new SQLException(String.format("%s hasn't been updated.", travelTypeDTO));
        }
        return TravelTypeDto.parse(travelTypeOptional.get());
    }

    @Override
    public void delete(Long id) {
        travelTypeRepository.delete(id);
    }
}
