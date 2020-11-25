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
        Company defaultCompany = Company.createCompany().build();

        Company testCompany = Company.createCompany()
                .withName("Travel for everyone")
                .withAddress("Kharkov, st. Free 101")
                .withAge(10)
                .build();

        Company companyNumberOne = companyRepository.get(1L).orElse(defaultCompany);
        LOGGER.info("First company: {}", companyNumberOne);

        List<Company> allCompany = companyRepository.getAll();
        LOGGER.info("All companies: {}", allCompany);

        Company createdCompany = companyRepository.create(testCompany).orElse(defaultCompany);
        LOGGER.info("Created company: {}", createdCompany);

        createdCompany.setAge(10_000);
        Company updatedCompany = companyRepository.update(createdCompany.getId(), createdCompany).orElse(defaultCompany);
        LOGGER.info("Updated company: {}", updatedCompany);

        companyRepository.delete(createdCompany.getId());
        LOGGER.info("Removed company: {}", createdCompany);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testCountryRepository() {
        Country defaultCountry = Country.createCountry().build();

        Country uganda = Country.createCountry()
                .withName("Uganda")
                .build();

        Country countryNumberOne = countryRepository.get(1L).orElse(defaultCountry);
        LOGGER.info("First country: {}", countryNumberOne);

        List<Country> allCountries = countryRepository.getAll();
        LOGGER.info("All countries: {}", allCountries);

        Country createdCountry = countryRepository.create(uganda)
                .orElse(defaultCountry);
        LOGGER.info("Created country: {}", createdCountry);

        createdCountry.setName("Uganda1");
        Country updatedCompany = countryRepository.update(createdCountry.getId(), createdCountry)
                .orElse(defaultCountry);
        LOGGER.info("Updated country: {}", updatedCompany);

        countryRepository.delete(createdCountry.getId());
        LOGGER.info("Removed country: {}", createdCountry);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testCountryTourRepository() {
        CountryTour defaultCountryTour = CountryTour.createCountryTour().build();

        CountryTour testCountryTour = CountryTour.createCountryTour()
                .withCountryId(5L)
                .withTourId(6L)
                .build();

        List<CountryTour> allCountryToursByCountryId = countryTourRepository.getAllByCountryId(1L);
        LOGGER.info("All countryTour by countryId: {}", allCountryToursByCountryId);

        List<CountryTour> allCountryToursByTourId = countryTourRepository.getAllByTourId(1L);
        LOGGER.info("All countryTour by tourId: {}", allCountryToursByTourId);

        List<CountryTour> allCountryTours = countryTourRepository.getAll();
        LOGGER.info("All countryTours: {}", allCountryTours);

        countryTourRepository.create(testCountryTour);
        LOGGER.info("Created countryTour: {}", testCountryTour);

        countryTourRepository.delete(testCountryTour);
        LOGGER.info("Removed countryTour by tourId: {}", testCountryTour);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testTourRepository() {
        Tour defaultTour = Tour.createTour().build();

        LocalDateTime localDateTime = LocalDateTime.parse("1990-11-22T10:00:00");
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        Tour testTour = Tour.createTour()
                .withPrice(2_675.0)
                .withAmountDay(3)
                .withDateDeparture(instant)
                .withCompanyId(1L)
                .withTravelTypeId(1L)
                .build();

        Tour tourNumberOne = tourRepository.getOne(1L).orElse(defaultTour);
        LOGGER.info("First tour: {}", tourNumberOne);

        List<Tour> allTours = tourRepository.getAll();
        LOGGER.info("All tours: {}", allTours);

        Tour createdTour = tourRepository.create(testTour).orElse(defaultTour);
        LOGGER.info("Created tour: {}", createdTour);

        createdTour.setAmountDay(666);
        Tour updatedTour = tourRepository.update(createdTour.getId(), createdTour).orElse(defaultTour);
        LOGGER.info("Updated tour: {}", updatedTour);

        tourRepository.delete(1L);
        LOGGER.info("Removed tour: {}", createdTour);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void testTravelTypeRepository() {
        TravelType defaultTravelType = TravelType.createTravelType().build();

        TravelType travelType = TravelType.createTravelType()
                .withType("By banana")
                .build();

        TravelType travelTypeNumberOne = travelTypeRepository.getOne(1L).orElse(defaultTravelType);
        LOGGER.info("First travelType: {}", travelTypeNumberOne);

        List<TravelType> allTravelTypes = travelTypeRepository.getAll();
        LOGGER.info("All travelTypes: {}", allTravelTypes);

        TravelType createdTravelTypes = travelTypeRepository.create(travelType).orElse(defaultTravelType);
        LOGGER.info("Created travelType: {}", createdTravelTypes);

        createdTravelTypes.setType("AAAAAA");
        TravelType updatedCompany = travelTypeRepository.update(createdTravelTypes.getId(), createdTravelTypes).orElse(defaultTravelType);
        LOGGER.info("Updated travelType: {}", updatedCompany);

        travelTypeRepository.delete(6L);
        LOGGER.info("Removed travelType: {}", createdTravelTypes);
    }
}
