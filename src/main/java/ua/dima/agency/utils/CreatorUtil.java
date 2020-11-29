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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreatorUtil {
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;
    private CountryRepository countryRepository;
    private ParserUtil parserUtil;

    public CreatorUtil(CountryTourRepository countryTourRepository,
                       TravelTypeRepository travelTypeRepository,
                       CountryRepository countryRepository,
                       ParserUtil parserUtil) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.countryRepository = countryRepository;
        this.parserUtil = parserUtil;
    }

    public void createMissingCountryTour(List<CountryDto> countriesDto, Long tourId) {
        getCountryIds(countriesDto).forEach(countryId -> countryTourRepository.create(tourId, countryId));
    }

    private List<Long> getCountryIds(List<CountryDto> countriesDto) {
        return countriesDto.stream()
                .map(CountryDto::getName)
                .map(countryRepository::get)
                .map(Optional::get)
                .map(Country::getId)
                .collect(Collectors.toList());
    }

    public void createMissingCountries(List<CountryDto> countriesDto) {
        for(CountryDto countryDto: countriesDto) {
            if(!doesExist(countryDto)) {
                Optional<Country> country = countryRepository.create(parserUtil.parse(countryDto));
            }
        }
    }

    private boolean doesExist(CountryDto countryDto) {
        return countryRepository.getAll().stream()
                .filter(country -> country.getName().equals(countryDto.getName()))
                .count() > 0;
    }

    public void createMissingTravelTypes(TravelTypeDto travelTypeDto) {
        List<String> allTravelTypes = travelTypeRepository.getAll().stream().map(TravelType::getType).collect(Collectors.toList());
        String travelTypeFromTour = travelTypeDto.getType();
        if(!allTravelTypes.contains(travelTypeFromTour)) {
            createTravelType(travelTypeFromTour);
        }
    }

    private void createTravelType(String travelType) {
        travelTypeRepository.create(TravelType.createTravelType().withType(travelType).build());
    }
}
