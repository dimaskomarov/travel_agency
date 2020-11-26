package ua.dima.agency.repositories;

import ua.dima.agency.domain.CountryTour;

import java.util.List;

public interface CountryTourRepository {
    List<CountryTour> getAll();
    List<CountryTour> getAllByCountryId(Long countryId);
    List<CountryTour> getAllByTourId(Long tourId);
    void create(CountryTour countryTour);
    void create(Long tourId, Long countryId);
    void delete(CountryTour countryTour);
    void deleteByTourId(Long tourId);
    void deleteByCountryId(Long countryId);
}
