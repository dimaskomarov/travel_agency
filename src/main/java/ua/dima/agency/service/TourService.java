package ua.dima.agency.service;

import ua.dima.agency.dto.TourDto;

import java.util.List;

public interface TourService {
    TourDto create(Long companyId, TourDto tourDTO);
    TourDto get(Long id);
    List<TourDto> getAll();
    TourDto update(Long companyId, TourDto tourDTO);
    void delete(Long id);
}
