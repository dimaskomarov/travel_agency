package ua.dima.agency.repositories;

import ua.dima.agency.dto.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    Optional<List<Tour>> getAll();
    Optional<Tour> getOne(Long id);
    Optional<Tour> create(Tour tour);
    Optional<Tour> update(Long id, Tour tour);
    void delete(Long id);
}
