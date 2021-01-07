package ua.dima.agency.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dima.agency.domain.Country;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.exceptions.ExecuteException;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @InjectMocks
    private CountryServiceImpl countryServiceImpl;
    @Mock
    private CountryTourRepository countryTourRepository;
    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getById_existedCountry_shouldReturnCountry() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraine = Country.create().withId(id).withName(name).build();
        when(countryRepository.get(id)).thenReturn(Optional.of(ukraine));

        CountryDto receivedCountryDto = countryServiceImpl.get(id);

        assertNotNull(receivedCountryDto);
        assertEquals(id, receivedCountryDto.getId());
        assertEquals(name, receivedCountryDto.getName());
    }

    @Test
    void getById_notExistedCountry_shouldReturnCountry() {
        Long id = 1L;
        when(countryRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> countryServiceImpl.get(id));
    }

    @Test
    void getByName_existedCountry_shouldReturnCountry() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraine = Country.create().withId(id).withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.of(ukraine));

        CountryDto receivedCountryDto = countryServiceImpl.get(name);

        assertNotNull(receivedCountryDto);
        assertEquals(id, receivedCountryDto.getId());
        assertEquals(name, receivedCountryDto.getName());
    }

    @Test
    void getByName_notExistedCountry_shouldThrowNoDataException() {
        String name = "Ukraine";
        when(countryRepository.get(name)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> countryServiceImpl.get(name));
    }

    @Test
    void getAll_validData_shouldReturnTwoCountries() {
        Long ukrId = 1L;
        String ukr = "Ukraine";
        Country ukraine = Country.create().withId(ukrId).withName(ukr).build();
        Long itaId = 2L;
        String ita = "Italy";
        Country italy = Country.create().withId(itaId).withName(ita).build();
        when(countryRepository.getAll()).thenReturn(Arrays.asList(ukraine, italy));

        List<CountryDto> countriesDto = countryServiceImpl.getAll();

        assertFalse(countriesDto.isEmpty());
        assertEquals(2, countriesDto.size());
        assertEquals(ukrId, countriesDto.get(0).getId());
        assertEquals(ukr, countriesDto.get(0).getName());
        assertEquals(itaId, countriesDto.get(1).getId());
        assertEquals(ita, countriesDto.get(1).getName());
    }

    @Test
    void getAll_validData_shouldThrowNoDataException() {
        when(countryRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> countryServiceImpl.getAll());
    }

    @Test
    void create_newCountry_shouldReturnNewCountry() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraineWithoutId = Country.create().withName(name).build();
        Country ukraine = Country.create().withId(id).withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.empty());
        when(countryRepository.create(ukraineWithoutId)).thenReturn(Optional.of(ukraine));

        CountryDto countryDtoForCreating = CountryDto.create().withName(name).build();
        CountryDto createdCountryDto = countryServiceImpl.create(countryDtoForCreating);

        assertNotNull(createdCountryDto);
        assertNotNull(createdCountryDto.getId());
        assertEquals(id, createdCountryDto.getId());
        assertEquals(name, createdCountryDto.getName());
    }

    @Test
    void create_existedCountry_shouldThrowExtraDataException() {
        String name = "Ukraine";
        Country ukraine = Country.create().withId(1L).withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.of(ukraine));

        CountryDto countryDtoForCreating = CountryDto.create().withName(name).build();
        assertThrows(ExtraDataException.class, () -> countryServiceImpl.create(countryDtoForCreating));
    }

    @Test
    void create_newCountry_shouldThrowExecuteException() {
        String name = "Ukraine";
        Country ukraineWithoutId = Country.create().withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.empty());
        when(countryRepository.create(ukraineWithoutId)).thenReturn(Optional.empty());

        CountryDto countryDtoForCreating = CountryDto.create().withName(name).build();
        assertThrows(ExecuteException.class, () -> countryServiceImpl.create(countryDtoForCreating));
    }

    @Test
    void update_newCountry_shouldReturnUpdatedCountry() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraineWithoutId = Country.create().withName(name).build();
        Country ukraine = Country.create().withId(id).withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.empty());
        when(countryRepository.update(id,  ukraineWithoutId)).thenReturn(Optional.of(ukraine));

        CountryDto countryDtoForUpdating = CountryDto.create().withName(name).build();
        CountryDto updatedCountryDto = countryServiceImpl.update(id, countryDtoForUpdating);

        assertNotNull(updatedCountryDto);
        assertEquals(id, updatedCountryDto.getId());
        assertEquals(name, updatedCountryDto.getName());
    }

    @Test
    void update_existedCountry_shouldThrowExtraDataException() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraine = Country.create().withId(id).withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.of(ukraine));

        CountryDto countryDtoForUpdating = CountryDto.create().withName(name).build();
        assertThrows(ExtraDataException.class, () -> countryServiceImpl.update(id, countryDtoForUpdating));
    }

    @Test
    void update_newCountry_shouldThrowExecuteException() {
        Long id = 1L;
        String name = "Ukraine";
        Country ukraineWithoutId = Country.create().withName(name).build();
        when(countryRepository.get(name)).thenReturn(Optional.empty());
        when(countryRepository.update(id,  ukraineWithoutId)).thenReturn(Optional.empty());

        CountryDto countryDtoForUpdating = CountryDto.create().withName(name).build();
        assertThrows(ExecuteException.class, () -> countryServiceImpl.update(id, countryDtoForUpdating));
    }

    @Test
    void delete_existedCountry_shouldDeleteCountry() {
        Long id = 1L;
        Country ukraine = Country.create().withId(id).withName("Ukraine").build();
        when(countryRepository.get(id)).thenReturn(Optional.of(ukraine))
                .thenReturn(Optional.empty());

        countryServiceImpl.delete(id);

        verify(countryTourRepository, times(1)).deleteByCountryId(id);
        verify(countryRepository, times(1)).delete(id);
    }

    @Test
    void delete_notExistedCountry_shouldThrowNoDataException() {
        Long id = 1L;
        when(countryRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> countryServiceImpl.delete(id));

        verify(countryTourRepository, never()).deleteByCountryId(id);
        verify(countryRepository, never()).delete(id);
    }

    @Test
    void delete_notExistedCountry_shouldThrowExecuteException() {
        Long id = 1L;
        Country ukraine = Country.create().withId(id).withName("Ukraine").build();
        when(countryRepository.get(id)).thenReturn(Optional.of(ukraine));

        assertThrows(ExecuteException.class, () -> countryServiceImpl.delete(id));
    }
}