package ua.dima.agency.repositories;

import ua.dima.agency.domain.TravelType;

import java.util.List;
import java.util.Optional;

public interface TravelTypeRepository {
    List<TravelType> getAll();
    Optional<TravelType> get(Long id);
    Optional<TravelType> getByType(String type);
    Optional<TravelType> create(TravelType travelType);
    Optional<TravelType> update(Long id, TravelType travelType);
    void delete(Long id);
}
