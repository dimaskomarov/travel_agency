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
import ua.dima.agency.service.impl.TourServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParserUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);
    private static final String LOG_PARSING_ERROR = "The parsing of {} with id {} has been failed.";
    private static final String MSG_PARSING_ERROR = "The parsing of %s with id %d has been failed.";
    private static CountryTourRepository countryTourRepository;
    private static TravelTypeRepository travelTypeRepository;
    private static CountryRepository countryRepository;
    private static TourRepository tourRepository;

    public ParserUtil(CountryTourRepository countryTourRepository,
                      TravelTypeRepository travelTypeRepository,
                      CountryRepository countryRepository,
                      TourRepository tourRepository) {
        ParserUtil.countryTourRepository = countryTourRepository;
        ParserUtil.travelTypeRepository = travelTypeRepository;
        ParserUtil.countryRepository = countryRepository;
        ParserUtil.tourRepository = tourRepository;
    }

    public static CompanyDto parse(Company company) {
        List<TourDto> toursDto = null;

        try {
            List<Tour> tours = tourRepository.getByCompanyId(company.getId());
            toursDto = tours.stream().map(tour -> parse(tour)).collect(Collectors.toList());
        } catch(Exception e) {
            LOGGER.error(LOG_PARSING_ERROR, "Company", company.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Company", company.getId()));
        }

        return collect(company, toursDto);
    }

    private static CompanyDto collect(Company company, List<TourDto> toursDto) {
        return CompanyDto.createCompanyDTO()
                .withId(company.getId())
                .withName(company.getName())
                .withAddress(company.getAddress())
                .withAge(company.getAge())
                .withToursDto(toursDto).build();
    }

    public static Company parse(CompanyDto companyDTO) {
        return Company.createCompany()
                .withId(companyDTO.getId())
                .withName(companyDTO.getName())
                .withAddress(companyDTO.getAddress())
                .withAge(companyDTO.getAge()).build();
    }

    public static TourDto parse(Tour tour) {
        TravelTypeDto travelTypeDto = null;
        List<CountryDto> countriesDto = null;
        try {
            Optional<TravelType> travelTypeOptional = travelTypeRepository.get(tour.getTravelTypeId());
            travelTypeDto = parse(travelTypeOptional.get());

            List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tour.getId());
            List<Country> counties = countryTours.stream().map(countryTour -> countryRepository.get(countryTour.getCountryId()).get()).collect(Collectors.toList());
            countriesDto = counties.stream().map(ParserUtil::parse).collect(Collectors.toList());
        } catch(Exception e) {
            LOGGER.error(LOG_PARSING_ERROR, "Tour", tour.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Tour", tour.getId()));
        }

        return collect(tour, travelTypeDto, countriesDto);
    }

    private static TourDto collect(Tour tour, TravelTypeDto travelTypeDto, List<CountryDto> countiesDto) {
        return TourDto.createTourDTO()
                .withPrice(tour.getPrice())
                .withAmountDays(tour.getAmountDay())
                .withDateDeparture(tour.getDateDeparture())
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(countiesDto).build();
    }

    public static Tour parse(TourDto tourDTO, Long companyId) {
        return Tour.createTour()
                .withId(tourDTO.getId())
                .withPrice(tourDTO.getPrice())
                .withAmountDay(tourDTO.getAmountDays())
                .withDateDeparture(tourDTO.getDateDeparture())
                .withCompanyId(companyId)
                .withTravelTypeId(tourDTO.getTravelTypeDto().getId()).build();
    }

    public static CountryDto parse(Country country) {
        return CountryDto.createCountryDTO()
                .withId(country.getId())
                .withName(country.getName()).build();
    }

    public static Country parse(CountryDto countryDTO) {
        return Country.createCountry()
                .withId(countryDTO.getId())
                .withName(countryDTO.getName()).build();
    }

    public static TravelTypeDto parse(TravelType travelType) {
        return TravelTypeDto.createTravelTypeDTO()
                .withId(travelType.getId())
                .withType(travelType.getType()).build();
    }

    public static TravelType parse(TravelTypeDto travelTypeDto) {
        return TravelType.createTravelType()
                .withId(travelTypeDto.getId())
                .withType(travelTypeDto.getType())
                .build();
    }
}
