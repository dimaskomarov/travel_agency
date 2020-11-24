package ua.dima.agency.service;

import ua.dima.agency.dto.TravelTypeDto;

public interface TravelTypeService {
    TravelTypeDto create(TravelTypeDto travelTypeDTO);
    TravelTypeDto get(Long id);
    TravelTypeDto update(Long id, TravelTypeDto travelTypeDTO);
    void delete(Long id);
}
