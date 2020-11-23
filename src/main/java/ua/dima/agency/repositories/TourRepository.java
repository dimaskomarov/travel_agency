package ua.dima.agency.repositories;

import ua.dima.agency.domain.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    List<Tour> getAll();//return type changed
    Optional<Tour> getOne(Long id);
    Optional<Tour> create(Tour tour);
    Optional<Tour> update(Long id, Tour tour);
    void delete(Long id);
}
