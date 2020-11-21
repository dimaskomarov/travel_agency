package ua.dima.agency.repositories;

import ua.dima.agency.domain.CountryTour;

import java.util.List;
import java.util.Optional;

public interface CountryTourRepository {
    Optional<List<CountryTour>> getAll();
    Optional<List<CountryTour>> getAllByCountryId(Long countryId);
    Optional<List<CountryTour>> getAllByTourId(Long tourId);
    void create(CountryTour countryTour);
    void delete(CountryTour countryTour);
}
