package ua.dima.agency.service;

import ua.dima.agency.dto.TourDto;

import java.util.List;

public interface TourService {
    TourDto get(Long companyId, Long id);
    List<TourDto> getAll(Long companyId);
    TourDto create(Long companyId, TourDto tourDTO);
    TourDto update(Long countryId, TourDto tourDTO, Long tourId);
    void delete(Long countryId, Long id);
}
