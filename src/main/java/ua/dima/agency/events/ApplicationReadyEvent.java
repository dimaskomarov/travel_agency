package ua.dima.agency.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.dima.agency.domain.*;
import ua.dima.agency.repositories.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class ApplicationReadyEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEvent.class);

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final CountryTourRepository countryTourRepository;
    private final TourRepository tourRepository;
    private final TravelTypeRepository travelTypeRepository;

    public ApplicationReadyEvent(CompanyRepository companyRepository,
                                 CountryRepository countryRepository,
                                 CountryTourRepository countryTourRepository,
                                 TourRepository tourRepository,
                                 TravelTypeRepository travelTypeRepository) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.countryTourRepository = countryTourRepository;
        this.tourRepository = tourRepository;
        this.travelTypeRepository = travelTypeRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testCompanyRepository() {
        Company defaultCompany = Company.builder().build();

        Company testCompany = Company.builder()
                .name("Travel for everyone")
                .address("Kharkov, st. Free 101")
                .age(10)
                .build();

        Company companyNumberOne = companyRepository.get(1L).orElse(defaultCompany);
        LOGGER.info("First company: {}", companyNumberOne);

        List<Company> allCompany = companyRepository.getAll();
        LOGGER.info("All companies: {}", allCompany);

        Company builderdCompany = companyRepository.create(testCompany).orElse(defaultCompany);
        LOGGER.info("builderd company: {}", builderdCompany);

        builderdCompany.setAge(10_000);
        Company updatedCompany = companyRepository.update(builderdCompany.getId(), builderdCompany).orElse(defaultCompany);
        LOGGER.info("Updated company: {}", updatedCompany);

        companyRepository.delete(builderdCompany.getId());
        LOGGER.info("Removed company: {}", builderdCompany);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testCountryRepository() {
        Country defaultCountry = Country.builder().build();

        Country uganda = Country.builder()
                .name("Uganda")
                .build();

        Country countryNumberOne = countryRepository.get(1L).orElse(defaultCountry);
        LOGGER.info("First country: {}", countryNumberOne);

        List<Country> allCountries = countryRepository.getAll();
        LOGGER.info("All countries: {}", allCountries);

        Country builderdCountry = countryRepository.create(uganda)
                .orElse(defaultCountry);
        LOGGER.info("builderd country: {}", builderdCountry);

        builderdCountry.setName("Uganda1");
        Country updatedCompany = countryRepository.update(builderdCountry.getId(), builderdCountry)
                .orElse(defaultCountry);
        LOGGER.info("Updated country: {}", updatedCompany);

        countryRepository.delete(builderdCountry.getId());
        LOGGER.info("Removed country: {}", builderdCountry);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testCountryTourRepository() {
        CountryTour defaultCountryTour = CountryTour.builder().build();

        CountryTour testCountryTour = CountryTour.builder()
                .countryId(5L)
                .tourId(6L)
                .build();

        List<CountryTour> allCountryToursByCountryId = countryTourRepository.getAllByCountryId(1L);
        LOGGER.info("All countryTour by countryId: {}", allCountryToursByCountryId);

        List<CountryTour> allCountryToursByTourId = countryTourRepository.getAllByTourId(1L);
        LOGGER.info("All countryTour by tourId: {}", allCountryToursByTourId);

        List<CountryTour> allCountryTours = countryTourRepository.getAll();
        LOGGER.info("All countryTours: {}", allCountryTours);

        countryTourRepository.create(testCountryTour);
        LOGGER.info("builderd countryTour: {}", testCountryTour);

        countryTourRepository.delete(testCountryTour);
        LOGGER.info("Removed countryTour by tourId: {}", testCountryTour);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testTourRepository() {
        Tour defaultTour = Tour.builder().build();

        LocalDateTime localDateTime = LocalDateTime.parse("1990-11-22T10:00:00");
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        Tour testTour = Tour.builder()
                .price(2_675.0)
                .amountDays(3)
                .dateDeparture(instant)
                .companyId(1L)
                .travelTypeId(1L)
                .build();

        Tour tourNumberOne = tourRepository.get(1L).orElse(defaultTour);
        LOGGER.info("First tour: {}", tourNumberOne);

        List<Tour> allTours = tourRepository.getAll();
        LOGGER.info("All tours: {}", allTours);

        Tour builderdTour = tourRepository.create(testTour).orElse(defaultTour);
        LOGGER.info("builderd tour: {}", builderdTour);

        builderdTour.setAmountDays(666);
        Tour updatedTour = tourRepository.update(builderdTour.getId(), builderdTour).orElse(defaultTour);
        LOGGER.info("Updated tour: {}", updatedTour);

        tourRepository.delete(1L);
        LOGGER.info("Removed tour: {}", builderdTour);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void testTravelTypeRepository() {
        TravelType defaultTravelType = TravelType.builder().build();

        TravelType travelType = TravelType.builder()
                .name("By banana")
                .build();

        TravelType travelTypeNumberOne = travelTypeRepository.get(1L).orElse(defaultTravelType);
        LOGGER.info("First travelType: {}", travelTypeNumberOne);

        List<TravelType> allTravelTypes = travelTypeRepository.getAll();
        LOGGER.info("All travelTypes: {}", allTravelTypes);

        TravelType builderdTravelTypes = travelTypeRepository.create(travelType).orElse(defaultTravelType);
        LOGGER.info("builderd travelType: {}", builderdTravelTypes);

        builderdTravelTypes.setName("AAAAAA");
        TravelType updatedCompany = travelTypeRepository.update(builderdTravelTypes.getId(), builderdTravelTypes).orElse(defaultTravelType);
        LOGGER.info("Updated travelType: {}", updatedCompany);

        travelTypeRepository.delete(6L);
        LOGGER.info("Removed travelType: {}", builderdTravelTypes);
    }
}
