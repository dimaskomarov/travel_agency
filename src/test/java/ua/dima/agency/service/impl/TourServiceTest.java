package ua.dima.agency.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dima.agency.domain.Company;
import ua.dima.agency.domain.CountryTour;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.service.CountryService;
import ua.dima.agency.service.TravelTypeService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TourServiceTest {

    @InjectMocks
    private TourServiceImpl tourServiceImpl;
    @Mock
    private CountryTourRepository countryTourRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private TravelTypeService travelTypeService;
    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void get_existedTourDto_shouldReturnTourDto() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        CountryTour ukrCountryTour = CountryTour.create()
                                     .withTourId(idTour)
                                     .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                                     .withTourId(idTour)
                                     .withCountryId(itaId).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(idTour)).thenReturn(Arrays.asList(tour));
        when(tourRepository.get(idTour, idCompany)).thenReturn(Optional.of(tour));
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(idTour)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto receivedTourDto = tourServiceImpl.get(idCompany, idTour);

        assertNotNull(receivedTourDto);
        assertEquals(idTour, receivedTourDto.getId());
        assertEquals(priceTour, receivedTourDto.getPrice());
        assertEquals(amountDaysTour, receivedTourDto.getAmountDays());
        assertEquals(dateDepartureTour, receivedTourDto.getDateDeparture());
        assertEquals(idType, receivedTourDto.getTravelTypeDto().getId());
        assertEquals(typeType, receivedTourDto.getTravelTypeDto().getType());
        assertEquals(2, receivedTourDto.getCountiesDto().size());
        assertEquals(ukrId, receivedTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, receivedTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, receivedTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, receivedTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void get_notExistedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Long idTour = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(idCompany, idTour));
    }

    @Test
    void get_notExistedTourDto_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Company company = Company.create().withId(idCompany).withName("Goodwin").withAge(10).build();
        Long idTour = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(idTour)).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(idCompany, idTour));
    }

    @Test
    void get_existedTourDto_shouldNoDataException() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(idTour)).thenReturn(Arrays.asList(tour));
        when(tourRepository.get(idTour, idCompany)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(idCompany, idTour));
    }

    @Test
    void getAll_existedCompany_shouldReturnToursDto() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        CountryTour ukrCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(itaId).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(idTour)).thenReturn(Arrays.asList(tour));
        when(countryTourRepository.getAllByTourId(idTour)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        List<TourDto> receivedToursDto = tourServiceImpl.getAll(idCompany);

        assertEquals(1, receivedToursDto.size());
        assertEquals(idTour, receivedToursDto.get(0).getId());
        assertEquals(priceTour, receivedToursDto.get(0).getPrice());
        assertEquals(amountDaysTour, receivedToursDto.get(0).getAmountDays());
        assertEquals(dateDepartureTour, receivedToursDto.get(0).getDateDeparture());
        assertEquals(idType, receivedToursDto.get(0).getTravelTypeDto().getId());
        assertEquals(typeType, receivedToursDto.get(0).getTravelTypeDto().getType());
        assertEquals(2, receivedToursDto.get(0).getCountiesDto().size());
        assertEquals(ukrId, receivedToursDto.get(0).getCountiesDto().get(0).getId());
        assertEquals(ukr, receivedToursDto.get(0).getCountiesDto().get(0).getName());
        assertEquals(itaId, receivedToursDto.get(0).getCountiesDto().get(1).getId());
        assertEquals(ita, receivedToursDto.get(0).getCountiesDto().get(1).getName());
    }

    @Test
    void getAll_notExistedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.getAll(idCompany));
    }

    @Test
    void getAll_existedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idTour = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(idTour)).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> tourServiceImpl.getAll(idCompany));
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    private Instant createInstance(String time, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, format).toInstant(ZoneOffset.UTC);
    }
}