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
import ua.dima.agency.exceptions.ExtraDataException;
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
import static org.mockito.Mockito.*;

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
        Long companyId = 1L;
        Company company = Company.create()
                .withId(companyId).withName("Goodwin")
                .withAge(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(typeId).withType(type)
                .build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.create()
                .withId(tourId).withPrice(price)
                .withAmountDays(amountDays)
                .withDateDeparture(dateDeparture)
                .withCompanyId(companyId)
                .withTravelTypeId(typeId).build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        CountryTour ukrCountryTour = CountryTour.create()
                                     .withTourId(tourId)
                                     .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                                     .withTourId(tourId)
                                     .withCountryId(itaId).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(tourId)).thenReturn(Arrays.asList(tour));
        when(tourRepository.get(tourId, companyId)).thenReturn(Optional.of(tour));
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(tourId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto receivedTourDto = tourServiceImpl.get(companyId, tourId);

        assertNotNull(receivedTourDto);
        assertEquals(tourId, receivedTourDto.getId());
        assertEquals(price, receivedTourDto.getPrice());
        assertEquals(amountDays, receivedTourDto.getAmountDays());
        assertEquals(dateDeparture, receivedTourDto.getDateDeparture());
        assertEquals(typeId, receivedTourDto.getTravelTypeDto().getId());
        assertEquals(type, receivedTourDto.getTravelTypeDto().getType());
        assertEquals(2, receivedTourDto.getCountiesDto().size());
        assertEquals(ukrId, receivedTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, receivedTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, receivedTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, receivedTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void get_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        Long tourId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(companyId, tourId));
    }

    @Test
    void get_notExistedTourDto_shouldThrowNoDataException() {
        Long companyId = 1L;
        Company company = Company.create().withId(companyId).withName("Goodwin").withAge(10).build();
        Long tourId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(tourId)).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(companyId, tourId));
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
    void create_existedCompany_shouldReturnNewTourDto() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourWithoutId = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(itaId).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(travelTypeService.get(typeType)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourWithoutId)).thenReturn(Optional.of(tour));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(idTour)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto createdTourDto = tourServiceImpl.create(idCompany, tourDtoWithoutId);

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).create(idTour, ukrId);
        verify(countryTourRepository, times(1)).create(idTour, itaId);
        assertNotNull(createdTourDto);
        assertEquals(idTour, createdTourDto.getId());
        assertEquals(priceTour, createdTourDto.getPrice());
        assertEquals(amountDaysTour, createdTourDto.getAmountDays());
        assertEquals(dateDepartureTour, createdTourDto.getDateDeparture());
        assertEquals(idType, createdTourDto.getTravelTypeDto().getId());
        assertEquals(typeType, createdTourDto.getTravelTypeDto().getType());
        assertEquals(2, createdTourDto.getCountiesDto().size());
        assertEquals(ukrId, createdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, createdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, createdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, createdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void create_notExistedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.create(idCompany, tourDtoWithoutId));
    }

    @Test
    void create_existedCompany_shouldThrowSQLException() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourWithoutId = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(travelTypeService.get(typeType)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourWithoutId)).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> tourServiceImpl.create(idCompany, tourDtoWithoutId));

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
    }

    @Test
    void create_tourWithExistedTravelTypeAndCountriesAndCountryTour_shouldReturnNewTourDto() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourWithoutId = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(itaId).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        doThrow(new ExtraDataException("")).when(countryService).create(italy);
        doThrow(new ExtraDataException("")).when(countryService).create(ukraine);
        doThrow(new ExtraDataException("")).when(travelTypeService).create(travelTypeDto);
        when(travelTypeService.get(typeType)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourWithoutId)).thenReturn(Optional.of(tour));
        doThrow(new ExtraDataException("")).when(countryTourRepository).create(idTour, ukrId);
        doThrow(new ExtraDataException("")).when(countryTourRepository).create(idTour, itaId);
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(idTour)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto createdTourDto = tourServiceImpl.create(idCompany, tourDtoWithoutId);


        assertNotNull(createdTourDto);
        assertEquals(idTour, createdTourDto.getId());
        assertEquals(priceTour, createdTourDto.getPrice());
        assertEquals(amountDaysTour, createdTourDto.getAmountDays());
        assertEquals(dateDepartureTour, createdTourDto.getDateDeparture());
        assertEquals(idType, createdTourDto.getTravelTypeDto().getId());
        assertEquals(typeType, createdTourDto.getTravelTypeDto().getType());
        assertEquals(2, createdTourDto.getCountiesDto().size());
        assertEquals(ukrId, createdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, createdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, createdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, createdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_existedCompany_shouldReturnUpdatedTourDto() {
        Long idCompany = 1L;
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourBeforeUpdate = Tour.create()
                .withPrice(1.0)
                .withAmountDays(1)
                .withDateDeparture(createInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tourForUpdate = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(itaId).build();
        when(tourRepository.get(idTour)).thenReturn(Optional.of(tourBeforeUpdate));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(idTour, tourForUpdate)).thenReturn(Optional.of(tour));
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(idType)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto createdTourDto = tourServiceImpl.update(tourDtoWithoutId, idTour);

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).deleteByTourId(idTour);
        verify(countryTourRepository, times(1)).create(idTour, ukrId);
        verify(countryTourRepository, times(1)).create(idTour, itaId);
        assertNotNull(createdTourDto);
        assertEquals(idTour, createdTourDto.getId());
        assertEquals(priceTour, createdTourDto.getPrice());
        assertEquals(amountDaysTour, createdTourDto.getAmountDays());
        assertEquals(dateDepartureTour, createdTourDto.getDateDeparture());
        assertEquals(idType, createdTourDto.getTravelTypeDto().getId());
        assertEquals(typeType, createdTourDto.getTravelTypeDto().getType());
        assertEquals(2, createdTourDto.getCountiesDto().size());
        assertEquals(ukrId, createdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, createdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, createdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, createdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_notExistedTour_shouldThrowNoDataException() {
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        when(tourRepository.get(idTour)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.update(tourDtoWithoutId, idTour));
    }

    @Test
    void update_tourWithUpdatedTravelTypeAndCountriesAndCountryTour_shouldReturnNewTourDto() {
        Long idCompany = 1L;
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourBeforeUpdate = Tour.create()
                .withPrice(1.0)
                .withAmountDays(1)
                .withDateDeparture(createInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tourForUpdate = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tour = Tour.create()
                .withId(idTour).withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.create()
                .withTourId(idTour)
                .withCountryId(itaId).build();
        when(tourRepository.get(idTour)).thenReturn(Optional.of(tourBeforeUpdate));
        doThrow(new ExtraDataException("")).when(countryService).create(ukraine);
        doThrow(new ExtraDataException("")).when(countryService).create(italy);
        doThrow(new ExtraDataException("")).when(travelTypeService).create(travelTypeDto);
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(idTour, tourForUpdate)).thenReturn(Optional.of(tour));
        when(travelTypeService.get(idType)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(idType)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto createdTourDto = tourServiceImpl.update(tourDtoWithoutId, idTour);

        verify(countryTourRepository, times(1)).deleteByTourId(idTour);
        verify(countryTourRepository, times(1)).create(idTour, ukrId);
        verify(countryTourRepository, times(1)).create(idTour, itaId);
        assertNotNull(createdTourDto);
        assertEquals(idTour, createdTourDto.getId());
        assertEquals(priceTour, createdTourDto.getPrice());
        assertEquals(amountDaysTour, createdTourDto.getAmountDays());
        assertEquals(dateDepartureTour, createdTourDto.getDateDeparture());
        assertEquals(idType, createdTourDto.getTravelTypeDto().getId());
        assertEquals(typeType, createdTourDto.getTravelTypeDto().getType());
        assertEquals(2, createdTourDto.getCountiesDto().size());
        assertEquals(ukrId, createdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, createdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, createdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, createdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_existedTour_shouldThrowSQLException() {
        Long idCompany = 1L;
        Long idType = 1L;
        String typeType = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create()
                .withId(idType).withType(typeType)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long idTour = 1L;
        Double priceTour = 200.0;
        Integer amountDaysTour = 5;
        Instant dateDepartureTour = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourBeforeUpdate = Tour.create()
                .withPrice(1.0)
                .withAmountDays(1)
                .withDateDeparture(createInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        Tour tourForUpdate = Tour.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withCompanyId(idCompany)
                .withTravelTypeId(idType).build();
        TourDto tourDtoWithoutId = TourDto.create()
                .withPrice(priceTour)
                .withAmountDays(amountDaysTour)
                .withDateDeparture(dateDepartureTour)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy, ukraine)).build();
        when(tourRepository.get(idTour)).thenReturn(Optional.of(tourBeforeUpdate));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(idTour, tourForUpdate)).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> tourServiceImpl.update(tourDtoWithoutId, idTour));
        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).deleteByTourId(idTour);
        verify(countryTourRepository, times(1)).create(idTour, ukrId);
        verify(countryTourRepository, times(1)).create(idTour, itaId);
    }

    @Test
    void deleteByCompanyIdAndTourId_existedTour_shouldRemoveCurrentTour() {
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
        when(tourRepository.get(idCompany, idTour)).thenReturn(Optional.of(tour));

        tourServiceImpl.delete(idCompany, idTour);

        verify(countryTourRepository, times(1)).deleteByTourId(idTour);
        verify(tourRepository, times(1)).delete(idTour);
    }

    @Test
    void deleteByCompanyIdAndTourId_notExistedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Long idTour = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.delete(idCompany, idTour));

        verify(countryTourRepository, never()).deleteByTourId(idTour);
        verify(tourRepository, never()).delete(idTour);
    }

    @Test
    void deleteByCompanyIdAndTourId_existedCompanyWithoutAnyTours_shouldThrowNoDataException() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        Long idTour = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        when(tourRepository.get(idCompany, idTour)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.delete(idCompany, idTour));

        verify(countryTourRepository, never()).deleteByTourId(idTour);
        verify(tourRepository, never()).delete(idTour);
    }

    @Test
    void deleteByCompanyIdAndTourId_existedTour_shouldThrowSQLException() {
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
        when(tourRepository.get(idCompany, idTour)).thenReturn(Optional.of(tour));
        doThrow(new SQLException("")).when(countryTourRepository).deleteByTourId(idTour);

        assertThrows(SQLException.class, () -> tourServiceImpl.delete(idCompany, idTour));

        verify(tourRepository, never()).delete(idTour);
    }

    @Test
    void deleteByCompanyId_existedCompany_shouldRemoveAllTourByCompanyId() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));

        tourServiceImpl.delete(idCompany);

        verify(tourRepository, times(1)).deleteByCompanyId(idCompany);
    }

    @Test
    void deleteByCompanyId_notExistedCompany_shouldThrowNoDataException() {
        Long idCompany = 1L;
        when(companyRepository.get(idCompany)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.delete(idCompany));
        verify(tourRepository, never()).deleteByCompanyId(idCompany);
    }

    @Test
    void deleteByCompanyId_existedCompany_shouldThrowSQLException() {
        Long idCompany = 1L;
        Company company = Company.create()
                .withId(idCompany).withName("Goodwin")
                .withAge(10).build();
        when(companyRepository.get(idCompany)).thenReturn(Optional.of(company));
        doThrow(new SQLException("")).when(tourRepository).deleteByCompanyId(idCompany);

        assertThrows(SQLException.class, () -> tourServiceImpl.delete(idCompany));
    }

    private Instant createInstance(String time, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, format).toInstant(ZoneOffset.UTC);
    }
}