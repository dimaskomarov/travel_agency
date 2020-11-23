package ua.dima.agency.service;

import ua.dima.agency.dto.TourDTO;

public interface TourService {
    TourDTO create(TourDTO tourDTO);
    TourDTO get(Long id);
    TourDTO update(Long id, TourDTO tourDTO);
    void delete(Long id);
}
