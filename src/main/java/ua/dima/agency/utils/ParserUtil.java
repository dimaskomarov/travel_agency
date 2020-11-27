package ua.dima.agency.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.dima.agency.domain.*;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ParseException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParserUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserUtil.class);
    private static final String LOG_PARSING_ERROR = "The parsing of {} with id {} has been failed.";
    private static final String MSG_PARSING_ERROR = "The parsing of %s with id %d has been failed.";
    private static CountryTourRepository countryTourRepository;
    private static TravelTypeRepository travelTypeRepository;
    private static CountryRepository countryRepository;
    private static TourRepository tourRepository;

    private ParserUtil() {
        //empty constructor
    }

    public static CompanyDto parse(Company company) {
        List<TourDto> toursDto = null;

        try {
            List<Tour> tours = tourRepository.getByCompanyId(company.getId());
            toursDto = tours.stream().map(ParserUtil::parse).collect(Collectors.toList());
        } catch(RuntimeException e) {
            LOGGER.error(LOG_PARSING_ERROR, "Company", company.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Company", company.getId()));
        }

        return collect(company, toursDto);
    }

    private static CompanyDto collect(Company company, List<TourDto> toursDto) {
        return CompanyDto.createCompanyDTO()
                .withName(company.getName())
                .withAddress(company.getAddress())
                .withAge(company.getAge())
                .withToursDto(toursDto).build();
    }

    public static Company parse(CompanyDto companyDTO) {
        return Company.createCompany()
                .withName(companyDTO.getName())
                .withAddress(companyDTO.getAddress())
                .withAge(companyDTO.getAge()).build();
    }

    public static TourDto parse(Tour tour) {
        TravelTypeDto travelTypeDto = null;
        List<CountryDto> countriesDto = null;
        try {
            Optional<TravelType> travelTypeOptional = travelTypeRepository.get(tour.getTravelTypeId());
            if(travelTypeOptional.isPresent()) {
                travelTypeDto = parse(travelTypeOptional.get());
            }

            List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tour.getId());
            List<Country> counties = countryTours.stream().map(countryTour -> countryRepository.get(countryTour.getCountryId()).get()).collect(Collectors.toList());
            countriesDto = counties.stream().map(ParserUtil::parse).collect(Collectors.toList());
        } catch(RuntimeException e) {
            LOGGER.error(LOG_PARSING_ERROR, "Tour", tour.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Tour", tour.getId()));
        }

        return collect(tour, travelTypeDto, countriesDto);
    }

    private static TourDto collect(Tour tour, TravelTypeDto travelTypeDto, List<CountryDto> countiesDto) {
        return TourDto.createTourDTO()
                .withId(tour.getId())
                .withPrice(tour.getPrice())
                .withAmountDays(tour.getAmountDay())
                .withDateDeparture(tour.getDateDeparture())
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(countiesDto).build();
    }

    public static Tour parse(TourDto tourDTO, Long companyId) {
        Optional<TravelType> createdTravelType = travelTypeRepository.getByName(tourDTO.getTravelTypeDto().getType());
        Long travelTypeId = createdTravelType.isPresent() ? createdTravelType.get().getId():0L;

        return Tour.createTour()
                .withPrice(tourDTO.getPrice())
                .withAmountDay(tourDTO.getAmountDays())
                .withDateDeparture(tourDTO.getDateDeparture())
                .withCompanyId(companyId)
                .withTravelTypeId(travelTypeId).build();
    }

    public static CountryDto parse(Country country) {
        return CountryDto.createCountryDTO()
                .withName(country.getName()).build();
    }

    public static Country parse(CountryDto countryDTO) {
        return Country.createCountry()
                .withName(countryDTO.getName()).build();
    }

    public static TravelTypeDto parse(TravelType travelType) {
        return TravelTypeDto.createTravelTypeDTO()
                .withType(travelType.getType()).build();
    }

    public static TravelType parse(TravelTypeDto travelTypeDto) {
        return TravelType.createTravelType()
                .withType(travelTypeDto.getType())
                .build();
    }
}
