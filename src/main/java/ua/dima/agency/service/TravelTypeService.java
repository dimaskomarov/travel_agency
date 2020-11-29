package ua.dima.agency.service;

import ua.dima.agency.dto.TravelTypeDto;

import java.util.List;

public interface TravelTypeService {
    TravelTypeDto get(Long id);
    List<TravelTypeDto> getAll();
    TravelTypeDto create(TravelTypeDto travelTypeDTO);
    TravelTypeDto update(Long id, TravelTypeDto travelTypeDTO);
    void delete(Long id);
}
