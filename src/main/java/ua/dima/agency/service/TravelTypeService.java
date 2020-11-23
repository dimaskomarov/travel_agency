package ua.dima.agency.service;

import ua.dima.agency.dto.TravelTypeDTO;

public interface TravelTypeService {
    TravelTypeDTO create(TravelTypeDTO travelTypeDTO);
    TravelTypeDTO get(Long id);
    TravelTypeDTO update(Long id, TravelTypeDTO travelTypeDTO);
    void delete(Long id);
}
