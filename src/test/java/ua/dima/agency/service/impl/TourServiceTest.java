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
import ua.dima.agency.exceptions.ExecuteException;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
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
    void get_existedTour_shouldReturnTour() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                                     .tourId(tourId)
                                     .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                                     .tourId(tourId)
                                     .countryId(itaId).build();
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
        assertEquals(type, receivedTourDto.getTravelTypeDto().getName());
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
    void get_notExistedTour_shouldThrowNoDataException() {
        Long companyId = 1L;
        Company company = Company.builder().id(companyId).name("Goodwin").age(10).build();
        Long tourId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(tourId)).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(companyId, tourId));
    }

    @Test
    void get_existedTour_shouldNoDataException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long tourId = 1L;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.builder()
                .id(tourId).price(200.0)
                .amountDays(5)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(1L).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(tourId)).thenReturn(Arrays.asList(tour));
        when(tourRepository.get(tourId, companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.get(companyId, tourId));
    }

    @Test
    void getAll_existedCompany_shouldReturnTours() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(itaId).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(tourId)).thenReturn(Arrays.asList(tour));
        when(countryTourRepository.getAllByTourId(tourId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        List<TourDto> receivedToursDto = tourServiceImpl.getAll(companyId);

        assertEquals(1, receivedToursDto.size());
        assertEquals(tourId, receivedToursDto.get(0).getId());
        assertEquals(price, receivedToursDto.get(0).getPrice());
        assertEquals(amountDays, receivedToursDto.get(0).getAmountDays());
        assertEquals(dateDeparture, receivedToursDto.get(0).getDateDeparture());
        assertEquals(typeId, receivedToursDto.get(0).getTravelTypeDto().getId());
        assertEquals(type, receivedToursDto.get(0).getTravelTypeDto().getName());
        assertEquals(2, receivedToursDto.get(0).getCountiesDto().size());
        assertEquals(ukrId, receivedToursDto.get(0).getCountiesDto().get(0).getId());
        assertEquals(ukr, receivedToursDto.get(0).getCountiesDto().get(0).getName());
        assertEquals(itaId, receivedToursDto.get(0).getCountiesDto().get(1).getId());
        assertEquals(ita, receivedToursDto.get(0).getCountiesDto().get(1).getName());
    }

    @Test
    void getAll_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.getAll(companyId));
    }

    @Test
    void getAll_existedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(1L)).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> tourServiceImpl.getAll(companyId));
    }

    @Test
    void builder_existedCompany_shouldReturnNewTour() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourTobuilder = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour builderdTour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto tourDtoTobuilder = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(itaId).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(travelTypeService.get(type)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourTobuilder)).thenReturn(Optional.of(builderdTour));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(tourId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto builderdTourDto = tourServiceImpl.create(companyId, tourDtoTobuilder);

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).create(tourId, ukrId);
        verify(countryTourRepository, times(1)).create(tourId, itaId);
        assertNotNull(builderdTourDto);
        assertEquals(tourId, builderdTourDto.getId());
        assertEquals(price, builderdTourDto.getPrice());
        assertEquals(amountDays, builderdTourDto.getAmountDays());
        assertEquals(dateDeparture, builderdTourDto.getDateDeparture());
        assertEquals(typeId, builderdTourDto.getTravelTypeDto().getId());
        assertEquals(type, builderdTourDto.getTravelTypeDto().getName());
        assertEquals(2, builderdTourDto.getCountiesDto().size());
        assertEquals(ukrId, builderdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, builderdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, builderdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, builderdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void builder_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(1L).name(type)
                .build();
        Long ukrId = 1L;
        CountryDto ukraine = CountryDto.builder().id(ukrId).name("Ukraine").build();
        Long itaId = 2L;
        CountryDto italy = CountryDto.builder().id(itaId).name("Italy").build();
        Long tourId = 1L;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDtoTobuilder = TourDto.builder()
                .price(200.0)
                .amountDays(5)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.create(companyId, tourDtoTobuilder));
        verify(countryService, never()).create(italy);
        verify(countryService, never()).create(ukraine);
        verify(travelTypeService, never()).create(travelTypeDto);
        verify(countryTourRepository, never()).create(tourId, ukrId);
        verify(countryTourRepository, never()).create(tourId, itaId);
    }

    @Test
    void builder_existedCompany_shouldThrowExecuteException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourTobuilder = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto tourDtoTobuilder = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(travelTypeService.get(type)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourTobuilder)).thenReturn(Optional.empty());

        assertThrows(ExecuteException.class, () -> tourServiceImpl.create(companyId, tourDtoTobuilder));

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, never()).create(tourId, ukrId);
        verify(countryTourRepository, never()).create(tourId, itaId);
    }

    @Test
    void builder_tourWithExistedTravelTypeAndCountriesAndCountryTour_shouldReturnNewTour() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourTobuilder = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour builderdTour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto tourDtoTobuilder = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(itaId).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        doThrow(new ExtraDataException("")).when(countryService).create(italy);
        doThrow(new ExtraDataException("")).when(countryService).create(ukraine);
        doThrow(new ExtraDataException("")).when(travelTypeService).create(travelTypeDto);
        when(travelTypeService.get(type)).thenReturn(travelTypeDto);
        when(tourRepository.create(tourTobuilder)).thenReturn(Optional.of(builderdTour));
        doThrow(new ExtraDataException("")).when(countryTourRepository).create(tourId, ukrId);
        doThrow(new ExtraDataException("")).when(countryTourRepository).create(tourId, itaId);
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(tourId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto builderdTourDto = tourServiceImpl.create(companyId, tourDtoTobuilder);

        assertNotNull(builderdTourDto);
        assertEquals(tourId, builderdTourDto.getId());
        assertEquals(price, builderdTourDto.getPrice());
        assertEquals(amountDays, builderdTourDto.getAmountDays());
        assertEquals(dateDeparture, builderdTourDto.getDateDeparture());
        assertEquals(typeId, builderdTourDto.getTravelTypeDto().getId());
        assertEquals(type, builderdTourDto.getTravelTypeDto().getName());
        assertEquals(2, builderdTourDto.getCountiesDto().size());
        assertEquals(ukrId, builderdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, builderdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, builderdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, builderdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_existedCompany_shouldReturnUpdatedTour() {
        Long companyId = 1L;
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour oldTour = Tour.builder()
                .id(tourId)
                .price(1.0)
                .amountDays(1)
                .dateDeparture(builderInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour newTourToUpdate = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour updatedTour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto newTourDtoToUpdate = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(itaId).build();
        when(tourRepository.get(tourId)).thenReturn(Optional.of(oldTour));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(tourId, newTourToUpdate)).thenReturn(Optional.of(updatedTour));
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(typeId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto builderdTourDto = tourServiceImpl.update(newTourDtoToUpdate, tourId);

        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(countryTourRepository, times(1)).create(tourId, ukrId);
        verify(countryTourRepository, times(1)).create(tourId, itaId);
        assertNotNull(builderdTourDto);
        assertEquals(tourId, builderdTourDto.getId());
        assertEquals(price, builderdTourDto.getPrice());
        assertEquals(amountDays, builderdTourDto.getAmountDays());
        assertEquals(dateDeparture, builderdTourDto.getDateDeparture());
        assertEquals(typeId, builderdTourDto.getTravelTypeDto().getId());
        assertEquals(type, builderdTourDto.getTravelTypeDto().getName());
        assertEquals(2, builderdTourDto.getCountiesDto().size());
        assertEquals(ukrId, builderdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, builderdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, builderdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, builderdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_notExistedTour_shouldThrowNoDataException() {
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(1L).name("by banana")
                .build();
        Long ukrId = 1L;
        CountryDto ukraine = CountryDto.builder().id(ukrId).name("Ukraine").build();
        Long itaId = 2L;
        CountryDto italy = CountryDto.builder().id(itaId).name("Italy").build();
        Long tourId = 1L;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDtoWithoutId = TourDto.builder()
                .price( 200.0)
                .amountDays(5)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        when(tourRepository.get(tourId)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.update(tourDtoWithoutId, tourId));
        verify(countryService, never()).create(italy);
        verify(countryService, never()).create(ukraine);
        verify(travelTypeService, never()).create(travelTypeDto);
        verify(countryTourRepository, never()).deleteByTourId(tourId);
        verify(countryTourRepository, never()).create(tourId, ukrId);
        verify(countryTourRepository, never()).create(tourId, itaId);
    }

    @Test
    void update_tourWithUpdatedTravelTypeAndCountriesAndCountryTour_shouldReturnNewTour() {
        Long companyId = 1L;
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name(type)
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour oldTour = Tour.builder()
                .price(1.0)
                .amountDays(1)
                .dateDeparture(builderInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour newTourForUpdate = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour updatedTour = Tour.builder()
                .id(tourId).price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto tourDtoToUpdate = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        CountryTour ukrCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(ukrId).build();
        CountryTour itaCountryTour = CountryTour.builder()
                .tourId(tourId)
                .countryId(itaId).build();
        when(tourRepository.get(tourId)).thenReturn(Optional.of(oldTour));
        doThrow(new ExtraDataException("")).when(countryService).create(ukraine);
        doThrow(new ExtraDataException("")).when(countryService).create(italy);
        doThrow(new ExtraDataException("")).when(travelTypeService).create(travelTypeDto);
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(tourId, newTourForUpdate)).thenReturn(Optional.of(updatedTour));
        when(travelTypeService.get(typeId)).thenReturn(travelTypeDto);
        when(countryTourRepository.getAllByTourId(typeId)).thenReturn(Arrays.asList(ukrCountryTour, itaCountryTour));
        when(countryService.get(ukrId)).thenReturn(ukraine);
        when(countryService.get(itaId)).thenReturn(italy);

        TourDto builderdTourDto = tourServiceImpl.update(tourDtoToUpdate, tourId);

        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(countryTourRepository, times(1)).create(tourId, ukrId);
        verify(countryTourRepository, times(1)).create(tourId, itaId);
        assertNotNull(builderdTourDto);
        assertEquals(tourId, builderdTourDto.getId());
        assertEquals(price, builderdTourDto.getPrice());
        assertEquals(amountDays, builderdTourDto.getAmountDays());
        assertEquals(dateDeparture, builderdTourDto.getDateDeparture());
        assertEquals(typeId, builderdTourDto.getTravelTypeDto().getId());
        assertEquals(type, builderdTourDto.getTravelTypeDto().getName());
        assertEquals(2, builderdTourDto.getCountiesDto().size());
        assertEquals(ukrId, builderdTourDto.getCountiesDto().get(0).getId());
        assertEquals(ukr, builderdTourDto.getCountiesDto().get(0).getName());
        assertEquals(itaId, builderdTourDto.getCountiesDto().get(1).getId());
        assertEquals(ita, builderdTourDto.getCountiesDto().get(1).getName());
    }

    @Test
    void update_existedTour_shouldThrowExecuteException() {
        Long companyId = 1L;
        Long typeId = 1L;
        TravelTypeDto travelTypeDto = TravelTypeDto.builder()
                .id(typeId).name("by banana")
                .build();
        Long ukrId = 1L;
        String ukr = "Ukraine";
        CountryDto ukraine = CountryDto.builder().id(ukrId).name(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.builder().id(itaId).name(ita).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour oldTour = Tour.builder()
                .price(1.0)
                .amountDays(1)
                .dateDeparture(builderInstance("1900-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss"))
                .companyId(companyId)
                .travelTypeId(typeId).build();
        Tour newTourToUpdate = Tour.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(typeId).build();
        TourDto tourDtoToUpdate = TourDto.builder()
                .price(price)
                .amountDays(amountDays)
                .dateDeparture(dateDeparture)
                .travelTypeDto(travelTypeDto)
                .countiesDto(Arrays.asList(italy, ukraine)).build();
        when(tourRepository.get(tourId)).thenReturn(Optional.of(oldTour));
        when(countryService.get(ukr)).thenReturn(ukraine);
        when(countryService.get(ita)).thenReturn(italy);
        when(tourRepository.update(tourId, newTourToUpdate)).thenReturn(Optional.empty());

        assertThrows(ExecuteException.class, () -> tourServiceImpl.update(tourDtoToUpdate, tourId));
        verify(countryService, times(1)).create(italy);
        verify(countryService, times(1)).create(ukraine);
        verify(travelTypeService, times(1)).create(travelTypeDto);
        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(countryTourRepository, times(1)).create(tourId, ukrId);
        verify(countryTourRepository, times(1)).create(tourId, itaId);
    }

    @Test
    void delete_existedTour_shouldDeleteCurrentTour() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long tourId = 1L;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourToDelete = Tour.builder()
                .id(tourId).price(200.0)
                .amountDays(5)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(1L).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company))
                .thenReturn(Optional.empty());
        when(tourRepository.get(companyId, tourId)).thenReturn(Optional.of(tourToDelete));

        tourServiceImpl.delete(companyId, tourId);

        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(tourRepository, times(1)).delete(tourId);
    }

    @Test
    void delete_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        Long tourId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.delete(companyId, tourId));

        verify(countryTourRepository, never()).deleteByTourId(tourId);
        verify(tourRepository, never()).delete(tourId);
    }

    @Test
    void delete_existedCompanyWithoutAnyTours_shouldThrowNoDataException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long tourId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.get(companyId, tourId)).thenReturn(Optional.empty());

        assertThrows( NoDataException.class, () -> tourServiceImpl.delete(companyId, tourId));

        verify(countryTourRepository, never()).deleteByTourId(tourId);
        verify(tourRepository, never()).delete(tourId);
    }

    @Test
    void delete_existedTour_shouldThrowExecuteException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        Long tourId = 1L;
        Instant dateDeparture = builderInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        Tour tourToDelete = Tour.builder()
                .id(tourId).price(200.0)
                .amountDays(5)
                .dateDeparture(dateDeparture)
                .companyId(companyId)
                .travelTypeId(1L).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.get(companyId, tourId)).thenReturn(Optional.of(tourToDelete));
        when(tourRepository.get(tourId)).thenReturn(Optional.of(tourToDelete));

        assertThrows(ExecuteException.class, () -> tourServiceImpl.delete(companyId, tourId));

        verify(tourRepository, times(1)).delete(tourId);
    }

    @Test
    void deleteAll_existedCompany_shouldDeleteAllTourByCompanyId() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(companyRepository.getAll()).thenReturn(Collections.emptyList());

        tourServiceImpl.deleteAll(companyId);

        verify(tourRepository, times(1)).deleteByCompanyId(companyId);
    }

    @Test
    void deleteAll_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> tourServiceImpl.deleteAll(companyId));
        verify(tourRepository, never()).deleteByCompanyId(companyId);
    }

    @Test
    void deleteAll_existedCompany_shouldThrowExecuteException() {
        Long companyId = 1L;
        Company company = Company.builder()
                .id(companyId).name("Goodwin")
                .age(10).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourRepository.getByCompanyId(companyId)).thenReturn(Arrays.asList(Tour.builder().build()));

        assertThrows(ExecuteException.class, () -> tourServiceImpl.deleteAll(companyId));
        verify(tourRepository, times(1)).deleteByCompanyId(companyId);
    }

    private Instant builderInstance(String time, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, format).toInstant(ZoneOffset.UTC);
    }
}