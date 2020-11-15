package ua.dima.agency.repositories;

import ua.dima.agency.domain.CountryTour;

import java.util.List;
import java.util.Optional;

public interface CountryTourRepository {
    Optional<List<CountryTour>> getAll();
    Optional<CountryTour> getOneByCountryId(Long countryId);
    Optional<CountryTour> getOneByTourId(Long tourId);
    Optional<CountryTour> create(CountryTour countryTour);
    Optional<CountryTour> updateByCountryId(Long countryId, CountryTour countryTour);
    Optional<CountryTour> updateByTourId(Long tourId, CountryTour countryTour);
    void deleteByCountryId(Long countryId);
    void deleteByTourId(Long tourId);
}
