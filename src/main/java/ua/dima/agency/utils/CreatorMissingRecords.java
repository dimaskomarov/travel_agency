package ua.dima.agency.utils;

import org.springframework.stereotype.Service;
import ua.dima.agency.domain.Country;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatorMissingRecords {
    private static CountryTourRepository countryTourRepository;
    private static TravelTypeRepository travelTypeRepository;
    private static CountryRepository countryRepository;

    private CreatorMissingRecords(CountryTourRepository countryTourRepository,
                   TravelTypeRepository travelTypeRepository,
                   CountryRepository countryRepository) {
        CreatorMissingRecords.countryTourRepository = countryTourRepository;
        CreatorMissingRecords.travelTypeRepository = travelTypeRepository;
        CreatorMissingRecords.countryRepository = countryRepository;
    }

    public static void createMissingCountryTour(List<CountryDto> countriesDto, Long tourId) {
        countriesDto.stream().map(Parser::parse).forEach(country -> countryTourRepository.create(tourId, country.getId()));
    }

    public static void createMissingCountries(List<CountryDto> countriesDto) {
        List<String> countriesNameFromTour = countriesDto.stream().map(CountryDto::getName).collect(Collectors.toList());
        List<String> allCountriesName = countryRepository.getAll().stream().map(Country::getName).collect(Collectors.toList());
        List<String> missingCountries = countriesNameFromTour.stream()
                .filter(countryFromTour -> !allCountriesName.contains(countryFromTour))
                .collect(Collectors.toList());

        if(!missingCountries.isEmpty()) {
            createCountries(missingCountries);
        }
    }

    private static void createCountries(List<String> countriesName) {
        countryRepository.createAllByName(countriesName);
    }

    public static void createMissingTravelTypes(TravelTypeDto travelTypeDto) {
        List<String> allTravelTypes = travelTypeRepository.getAll().stream().map(TravelType::getType).collect(Collectors.toList());
        String travelTypeFromTour = travelTypeDto.getType();
        if(!allTravelTypes.contains(travelTypeFromTour)) {
            createTravelType(travelTypeFromTour);
        }
    }

    private static void createTravelType(String travelType) {
        travelTypeRepository.create(TravelType.createTravelType().withType(travelType).build());
    }
}
