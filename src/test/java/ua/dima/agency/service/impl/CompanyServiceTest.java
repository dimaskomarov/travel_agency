package ua.dima.agency.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dima.agency.domain.Company;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ExecuteException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.service.TourService;

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

class CompanyServiceTest {

    @InjectMocks
    private CompanyServiceImpl companyService;
    @Mock
    private CountryTourRepository countryTourRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private TourService tourService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void get_existedCompany_shouldReturnCompany() {
        Long companyId = 1L;
        String name  = "Goodwin";
        Integer age = 10;
        Company company = Company.create()
                .withId(companyId).withName("Goodwin").withAge(age).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(typeId)
                .withType(type).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(price)
                .withAmountDays(amountDays)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        CompanyDto receivedCompanyDto = companyService.get(companyId);

        assertNotNull(receivedCompanyDto);
        assertEquals(companyId, receivedCompanyDto.getId());
        assertEquals(name, receivedCompanyDto.getName());
        assertEquals(age, receivedCompanyDto.getAge());
        assertEquals(1, receivedCompanyDto.getToursDto().size());
        assertEquals(1, receivedCompanyDto.getToursDto().get(0).getCountiesDto().size());
        assertEquals(tourId, receivedCompanyDto.getToursDto().get(0).getId());
        assertEquals(price, receivedCompanyDto.getToursDto().get(0).getPrice());
        assertEquals(amountDays, receivedCompanyDto.getToursDto().get(0).getAmountDays());
        assertEquals(dateDeparture, receivedCompanyDto.getToursDto().get(0).getDateDeparture());
        assertEquals(typeId, receivedCompanyDto.getToursDto().get(0).getTravelTypeDto().getId());
        assertEquals(type, receivedCompanyDto.getToursDto().get(0).getTravelTypeDto().getType());
        assertEquals(itaId, receivedCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getId());
        assertEquals(ita, receivedCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getName());
    }

    @Test
    void get_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> companyService.get(companyId));
    }

    @Test
    void getAll_validData_shouldReturnAllCompanies() {
        Long companyId = 1L;
        String name  = "Goodwin";
        Integer age = 10;
        Company company = Company.create()
                .withId(companyId).withName("Goodwin").withAge(age).build();
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(typeId)
                .withType(type).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(price)
                .withAmountDays(amountDays)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        when(companyRepository.getAll()).thenReturn(Arrays.asList(company));
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        List<CompanyDto> companies = companyService.getAll();

        assertEquals(1, companies.size());
        assertEquals(companyId, companies.get(0).getId());
        assertEquals(name, companies.get(0).getName());
        assertEquals(age, companies.get(0).getAge());
        assertEquals(1, companies.get(0).getToursDto().size());
        assertEquals(1, companies.get(0).getToursDto().get(0).getCountiesDto().size());
        assertEquals(tourId, companies.get(0).getToursDto().get(0).getId());
        assertEquals(price, companies.get(0).getToursDto().get(0).getPrice());
        assertEquals(amountDays, companies.get(0).getToursDto().get(0).getAmountDays());
        assertEquals(dateDeparture, companies.get(0).getToursDto().get(0).getDateDeparture());
        assertEquals(typeId, companies.get(0).getToursDto().get(0).getTravelTypeDto().getId());
        assertEquals(type, companies.get(0).getToursDto().get(0).getTravelTypeDto().getType());
        assertEquals(itaId, companies.get(0).getToursDto().get(0).getCountiesDto().get(0).getId());
        assertEquals(ita, companies.get(0).getToursDto().get(0).getCountiesDto().get(0).getName());
    }

    @Test
    void getAll_validData_shouldThrowNoDataException() {
        when(companyRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> companyService.getAll());
    }

    @Test
    void create_newCompany_shouldReturnNewCompany() {
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(typeId)
                .withType(type).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(price)
                .withAmountDays(amountDays)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        String name  = "Goodwin";
        Integer age = 10;
        Company companyToCreate = Company.create()
                .withName("Goodwin").withAge(age).build();
        Company createdCompany = Company.create()
                .withId(companyId).withName("Goodwin").withAge(age).build();
        CompanyDto companyDtoToCreate = CompanyDto.create()
                .withName("Goodwin").withAge(age).withToursDto(Arrays.asList(tourDto)).build();
        when(companyRepository.create(companyToCreate)).thenReturn(Optional.of(createdCompany));
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        CompanyDto createdCompanyDto = companyService.create(companyDtoToCreate);

        verify(tourService, times(1)).create(companyId, tourDto);
        assertNotNull(createdCompanyDto);
        assertEquals(companyId, createdCompanyDto.getId());
        assertEquals(name, createdCompanyDto.getName());
        assertEquals(age, createdCompanyDto.getAge());
        assertEquals(1, createdCompanyDto.getToursDto().size());
        assertEquals(1, createdCompanyDto.getToursDto().get(0).getCountiesDto().size());
        assertEquals(tourId, createdCompanyDto.getToursDto().get(0).getId());
        assertEquals(price, createdCompanyDto.getToursDto().get(0).getPrice());
        assertEquals(amountDays, createdCompanyDto.getToursDto().get(0).getAmountDays());
        assertEquals(dateDeparture, createdCompanyDto.getToursDto().get(0).getDateDeparture());
        assertEquals(typeId, createdCompanyDto.getToursDto().get(0).getTravelTypeDto().getId());
        assertEquals(type, createdCompanyDto.getToursDto().get(0).getTravelTypeDto().getType());
        assertEquals(itaId, createdCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getId());
        assertEquals(ita, createdCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getName());
    }

    @Test
    void create_newCompany_shouldThrowExecuteException() {
        CountryDto italy = CountryDto.create().withId(2L).withName("Italy").build();
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(1L)
                .withType("by banana").build();
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(1L)
                .withPrice(200.0).withAmountDays(5)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        Company companyToCreate = Company.create()
                .withName("Goodwin").withAge(10).build();
        CompanyDto companyDtoToCreate = CompanyDto.create()
                .withName("Goodwin").withAge(10).withToursDto(Arrays.asList(tourDto)).build();
        when(companyRepository.create(companyToCreate)).thenReturn(Optional.empty());

        assertThrows(ExecuteException.class, () -> companyService.create(companyDtoToCreate));

        verify(tourService, never()).create(companyId, tourDto);
    }

    @Test
    void updated_existedCompany_shouldReturnUpdatedCompany() {
        Long itaId = 2L;
        String ita = "Italy";
        CountryDto italy = CountryDto.create().withId(itaId).withName(ita).build();
        Long typeId = 1L;
        String type = "by banana";
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(typeId)
                .withType(type).build();
        Long tourId = 1L;
        Double price = 200.0;
        Integer amountDays = 5;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(price)
                .withAmountDays(amountDays)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        String name  = "Goodwin";
        Integer age = 10;
        Company oldCompany = Company.create()
                .withId(companyId).withName("NightCity").withAge(20).build();
        Company companyToUpdate = Company.create()
                .withName("Goodwin").withAge(age).build();
        Company updatedCompany = Company.create().withId(companyId)
                .withName("Goodwin").withAge(age).build();
        CompanyDto companyDtoToUpdate = CompanyDto.create()
                .withName("Goodwin").withAge(age).withToursDto(Arrays.asList(tourDto)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(oldCompany));
        when(companyRepository.update(companyId, companyToUpdate)).thenReturn(Optional.of(updatedCompany));
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        CompanyDto createdCompanyDto = companyService.update(companyId, companyDtoToUpdate);

        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(tourService, times(1)).deleteAll(companyId);
        verify(tourService, times(1)).create(companyId, tourDto);
        assertNotNull(createdCompanyDto);
        assertEquals(companyId, createdCompanyDto.getId());
        assertEquals(name, createdCompanyDto.getName());
        assertEquals(age, createdCompanyDto.getAge());
        assertEquals(1, createdCompanyDto.getToursDto().size());
        assertEquals(1, createdCompanyDto.getToursDto().get(0).getCountiesDto().size());
        assertEquals(tourId, createdCompanyDto.getToursDto().get(0).getId());
        assertEquals(price, createdCompanyDto.getToursDto().get(0).getPrice());
        assertEquals(amountDays, createdCompanyDto.getToursDto().get(0).getAmountDays());
        assertEquals(dateDeparture, createdCompanyDto.getToursDto().get(0).getDateDeparture());
        assertEquals(typeId, createdCompanyDto.getToursDto().get(0).getTravelTypeDto().getId());
        assertEquals(type, createdCompanyDto.getToursDto().get(0).getTravelTypeDto().getType());
        assertEquals(itaId, createdCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getId());
        assertEquals(ita, createdCompanyDto.getToursDto().get(0).getCountiesDto().get(0).getName());
    }

    @Test
    void updated_notExistedCompany_shouldThrowNoDataException() {
        CountryDto italy = CountryDto.create().withId(2L).withName("Italy").build();
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(1L)
                .withType("by banana").build();
        Long tourId = 1L;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(200.0).withAmountDays(5)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        CompanyDto companyDtoToUpdate = CompanyDto.create()
                .withName("Goodwin").withAge(10).withToursDto(Arrays.asList(tourDto)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> companyService.update(companyId, companyDtoToUpdate));

        verify(countryTourRepository, never()).deleteByTourId(tourId);
        verify(tourService, never()).deleteAll(companyId);
        verify(tourService, never()).create(companyId, tourDto);
    }

    @Test
    void updated_existedCompany_shouldThrowExecuteException() {
        CountryDto italy = CountryDto.create().withId(2L).withName("Italy").build();
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(1L)
                .withType("by banana").build();
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(1L)
                .withPrice(200.0).withAmountDays(5)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        Company oldCompany = Company.create()
                .withId(companyId).withName("NightCity").withAge(20).build();
        Company companyToUpdate = Company.create()
                .withName("Goodwin").withAge(10).build();
        CompanyDto companyDtoToUpdate = CompanyDto.create()
                .withName("Goodwin").withAge(10).withToursDto(Arrays.asList(tourDto)).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(oldCompany));
        when(companyRepository.update(companyId, companyToUpdate)).thenReturn(Optional.empty());

        assertThrows(ExecuteException.class, () -> companyService.update(companyId, companyDtoToUpdate));

        verify(tourService, never()).create(companyId, tourDto);
    }

    @Test
    void delete_existedCompany_shouldDeleteCompany() {
        CountryDto italy = CountryDto.create().withId(2L).withName("Italy").build();
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(1L)
                .withType("by banana").build();
        Long tourId = 1L;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(200.0).withAmountDays(5)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        Company company = Company.create()
                .withId(companyId).withName("NightCity").withAge(20).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company))
                .thenReturn(Optional.empty());
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        companyService.delete(companyId);

        verify(countryTourRepository, times(1)).deleteByTourId(tourId);
        verify(tourService, times(1)).deleteAll(companyId);
        verify(companyRepository, times(1)).delete(companyId);
    }

    @Test
    void delete_notExistedCompany_shouldThrowNoDataException() {
        Long companyId = 1L;
        when(companyRepository.get(companyId)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> companyService.delete(companyId));

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourService, never()).deleteAll(companyId);
        verify(companyRepository, never()).delete(companyId);
    }

    @Test
    void delete_existedCompany_shouldThrowExecuteException() {
        CountryDto italy = CountryDto.create().withId(2L).withName("Italy").build();
        TravelTypeDto travelTypeDto = TravelTypeDto.create().withId(1L)
                .withType("by banana").build();
        Long tourId = 1L;
        Instant dateDeparture = createInstance("2020-12-20 20:20:20", "yyyy-MM-dd HH:mm:ss");
        TourDto tourDto = TourDto.create().withId(tourId)
                .withPrice(200.0).withAmountDays(5)
                .withDateDeparture(dateDeparture)
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(Arrays.asList(italy)).build();
        Long companyId = 1L;
        Company company = Company.create()
                .withId(companyId).withName("NightCity").withAge(20).build();
        when(companyRepository.get(companyId)).thenReturn(Optional.of(company));
        when(tourService.getAll(companyId)).thenReturn(Arrays.asList(tourDto));

        assertThrows(ExecuteException.class, () -> companyService.delete(companyId));

        verify(tourService, times(1)).deleteAll(companyId);
        verify(companyRepository, times(1)).delete(companyId);
    }

    private Instant createInstance(String time, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, format).toInstant(ZoneOffset.UTC);
    }
}