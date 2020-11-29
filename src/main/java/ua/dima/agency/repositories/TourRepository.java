package ua.dima.agency.repositories;

import ua.dima.agency.domain.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    List<Tour> getAll();
    Optional<Tour> get(Long id);
    Optional<Tour> get(Long companyId, Long id);
    List<Tour> getByCompanyId(Long companyId);
    Optional<Tour> create(Tour tour);
    Optional<Tour> update(Long id, Tour tour);
    void delete(Long id);
    void deleteByTourTypeId(Long travelTypeId);
    void deleteByCompanyId(Long companyId);
}
