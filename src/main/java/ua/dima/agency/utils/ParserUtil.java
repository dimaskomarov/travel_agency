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
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;
    private CountryRepository countryRepository;
    private TourRepository tourRepository;

    public ParserUtil(CountryTourRepository countryTourRepository,
                      TravelTypeRepository travelTypeRepository,
                      CountryRepository countryRepository,
                      TourRepository tourRepository) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.countryRepository = countryRepository;
        this.tourRepository = tourRepository;
    }

    public List<CompanyDto> parseCompanies(List<Company> companies) {
        return companies.stream().map(this::parse).collect(Collectors.toList());
    }

    public CompanyDto parse(Company company) {
        List<TourDto> toursDto = null;

        try {
            List<Tour> tours = tourRepository.getByCompanyId(company.getId());
            toursDto = tours.stream().map(this::parse).collect(Collectors.toList());
        } catch(RuntimeException e) {
            LOGGER.error(LOG_PARSING_ERROR, "Company", company.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Company", company.getId()));
        }

        return collect(company, toursDto);
    }

    private CompanyDto collect(Company company, List<TourDto> toursDto) {
        return CompanyDto.createCompanyDTO()
                .withId(company.getId())
                .withName(company.getName())
                .withAddress(company.getAddress())
                .withAge(company.getAge())
                .withToursDto(toursDto).build();
    }

    public List<Company> parseCompaniesDto(List<CompanyDto> companiesDto) {
        return companiesDto.stream().map(this::parse).collect(Collectors.toList());
    }

    public Company parse(CompanyDto companyDTO) {
        return Company.createCompany()
                .withId(companyDTO.getId())
                .withName(companyDTO.getName())
                .withAddress(companyDTO.getAddress())
                .withAge(companyDTO.getAge()).build();
    }

    public List<TourDto> parseTours(List<Tour> tours) {
        return tours.stream().map(this::parse).collect(Collectors.toList());
    }

    public TourDto parse(Tour tour) {
        TravelTypeDto travelTypeDto = null;
        List<CountryDto> countriesDto = null;
        try {
            Optional<TravelType> travelTypeOptional = travelTypeRepository.get(tour.getTravelTypeId());
            if(travelTypeOptional.isPresent()) {
                travelTypeDto = parse(travelTypeOptional.get());
            }

            List<CountryTour> countryTours = countryTourRepository.getAllByTourId(tour.getId());
            List<Country> counties = countryTours.stream().map(countryTour -> countryRepository.get(countryTour.getCountryId()).get()).collect(Collectors.toList());
            countriesDto = counties.stream().map(this::parse).collect(Collectors.toList());
        } catch(RuntimeException e) {
            LOGGER.error(LOG_PARSING_ERROR, "Tour", tour.getId());
            throw new ParseException(String.format(MSG_PARSING_ERROR, "Tour", tour.getId()));
        }

        return collect(tour, travelTypeDto, countriesDto);
    }

    private TourDto collect(Tour tour, TravelTypeDto travelTypeDto, List<CountryDto> countiesDto) {
        return TourDto.createTourDTO()
                .withId(tour.getId())
                .withPrice(tour.getPrice())
                .withAmountDays(tour.getAmountDays())
                .withDateDeparture(tour.getDateDeparture())
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(countiesDto).build();
    }

    public List<Tour> parseToursDto(List<TourDto> toursDto, Long companyId) {
        return toursDto.stream().map(tourDto -> parse(tourDto, companyId)).collect(Collectors.toList());
    }

    public Tour parse(TourDto tourDTO, Long companyId) {
        Optional<TravelType> createdTravelType = travelTypeRepository.get(tourDTO.getTravelTypeDto().getType());
        Long travelTypeId = createdTravelType.isPresent() ? createdTravelType.get().getId():0L;

        return Tour.createTour()
                .withId(tourDTO.getId())
                .withPrice(tourDTO.getPrice())
                .withAmountDays(tourDTO.getAmountDays())
                .withDateDeparture(tourDTO.getDateDeparture())
                .withCompanyId(companyId)
                .withTravelTypeId(travelTypeId).build();
    }

    public List<CountryDto> parseCountry(List<Country> countries) {
        return countries.stream().map(this::parse).collect(Collectors.toList());
    }

    public CountryDto parse(Country country) {
        return CountryDto.createCountryDTO()
                .withId(country.getId())
                .withName(country.getName()).build();
    }

    public List<Country> parseCountriesDto(List<CountryDto> countriesDto) {
        return countriesDto.stream().map(this::parse).collect(Collectors.toList());
    }

    public Country parse(CountryDto countryDTO) {
        return Country.createCountry()
                .withId(countryDTO.getId())
                .withName(countryDTO.getName()).build();
    }

    public List<TravelTypeDto> parseTravelTypes(List<TravelType> travelTypes) {
        return travelTypes.stream().map(this::parse).collect(Collectors.toList());
    }

    public TravelTypeDto parse(TravelType travelType) {
        return TravelTypeDto.createTravelTypeDTO()
                .withId(travelType.getId())
                .withType(travelType.getType()).build();
    }

    public List<TravelType> parseTravelTypesDto(List<TravelTypeDto> travelTypesDto) {
        return travelTypesDto.stream().map(this::parse).collect(Collectors.toList());
    }

    public TravelType parse(TravelTypeDto travelTypeDto) {
        return TravelType.createTravelType()
                .withId(travelTypeDto.getId())
                .withType(travelTypeDto.getType())
                .build();
    }
}
