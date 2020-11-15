package ua.dima.agency.repositories;

import ua.dima.agency.dto.TravelType;

import java.util.List;
import java.util.Optional;

public interface TravelTypeRepository {
    Optional<List<TravelType>> getAll();
    Optional<TravelType> getOne(Long id);
    Optional<TravelType> create(TravelType travelType);
    Optional<TravelType> update(Long id, TravelType travelType);
    void delete(Long id);
}
