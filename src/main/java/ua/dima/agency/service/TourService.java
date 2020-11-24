package ua.dima.agency.service;

import ua.dima.agency.dto.TourDto;

public interface TourService {
    TourDto create(TourDto tourDTO);
    TourDto get(Long id);
    TourDto update(Long id, TourDto tourDTO);
    void delete(Long id);
}
