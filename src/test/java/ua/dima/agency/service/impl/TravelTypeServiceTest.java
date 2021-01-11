package ua.dima.agency.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ExecuteException;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TravelTypeServiceTest {

    @InjectMocks
    private TravelTypeServiceImpl travelTypeServiceImpl;
    @Mock
    private CountryTourRepository countryTourRepository;
    @Mock
    private TravelTypeRepository travelTypeRepository;
    @Mock
    private TourRepository tourRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getById_existedTravelType_shouldReturnTravelType() {
        Long id = 1L;
        String name = "by banana";
        TravelType banana = TravelType.builder().id(id).name(name).build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(banana));

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(id);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(name, receivedTravelTypeDto.getName());
    }

    @Test
    void getById_notExistedTravelType_shouldThrowNoDataException() {
        Long id = 1L;
        when(travelTypeRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(id));
    }

    @Test
    void getByType_existedTravelType_shouldReturnTravelType() {
        Long id = 1L;
        String name = "by banana";
        TravelType banana = TravelType.builder().id(id).name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.of(banana));

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(name);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(name, receivedTravelTypeDto.getName());
    }

    @Test
    void getByType_notExistedTravelType_shouldThrowNoDataException() {
        String name = "by banana";
        when(travelTypeRepository.get(name)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(name));
    }

    @Test
    void getAll_validData_shouldReturnTwoTravelTypes() {
        Long carpetId = 1L;
        String carpetName = "by magic carpet";
        TravelType magicCarpet = TravelType.builder().id(carpetId).name(carpetName).build();
        Long bananaId = 2L;
        String bananaName = "by banana";
        TravelType banana = TravelType.builder().id(bananaId).name(bananaName).build();
        when(travelTypeRepository.getAll()).thenReturn(Arrays.asList(magicCarpet, banana));

        List<TravelTypeDto> travelTypesDto = travelTypeServiceImpl.getAll();

        assertFalse(travelTypesDto.isEmpty());
        assertEquals(2, travelTypesDto.size());
        assertEquals(carpetId, travelTypesDto.get(0).getId());
        assertEquals(carpetName, travelTypesDto.get(0).getName());
        assertEquals(bananaId, travelTypesDto.get(1).getId());
        assertEquals(bananaName, travelTypesDto.get(1).getName());
    }

    @Test
    void getAll_validData_shouldThrowNoDataException() {
        when(travelTypeRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.getAll());
    }

    @Test
    void builder_newTravelType_shouldReturnNewTravelType() {
        String name = "by banana";
        TravelType bananaWithoutId = TravelType.builder().name(name).build();
        TravelType banana = TravelType.builder().id(1L).name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.empty());
        when(travelTypeRepository.create(bananaWithoutId)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDtoWithoutId = TravelTypeDto.builder().name(name).build();
        TravelTypeDto builderdTravelTypeDto = travelTypeServiceImpl.create(travelTypeDtoWithoutId);

        assertNotNull(builderdTravelTypeDto);
        assertNotNull(builderdTravelTypeDto.getId());
        assertEquals(name, builderdTravelTypeDto.getName());
    }

    @Test
    void builder_existedTravelType_shouldThrowExtraDataException() {
        String name = "by banana";
        TravelType banana = TravelType.builder().id(1L).name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.builder().name(name).build();

        assertThrows(ExtraDataException.class, () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void builder_newTravelType_shouldThrowExecuteException() {
        String name = "by banana";
        TravelType bananaWithoutId = TravelType.builder().name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.empty());
        when(travelTypeRepository.create(bananaWithoutId)).thenReturn(Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.builder().name(name).build();

        assertThrows(ExecuteException.class, () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void update_newTravelType_shouldReturnUpdatedTravelType() {
        Long id = 1L;
        String name = "by banana";
        TravelType bananaWithoutId = TravelType.builder().name(name).build();
        TravelType banana = TravelType.builder().id(id).name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.empty());
        when(travelTypeRepository.update(id, bananaWithoutId)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.builder().name(name).build();
        TravelTypeDto updatedTravelTypeDto = travelTypeServiceImpl.update(id, travelTypeDto);

        assertNotNull(updatedTravelTypeDto);
        assertNotNull(updatedTravelTypeDto.getId());
        assertEquals(id, updatedTravelTypeDto.getId());
        assertEquals(name, updatedTravelTypeDto.getName());
    }

    @Test
    void update_existedTravelType_shouldThrowExtraDataException() {
        Long id = 1L;
        String name = "by banana";
        TravelType banana = TravelType.builder().id(id).name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.builder().name(name).build();

        assertThrows(ExtraDataException.class, () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void update_newTravelType_shouldThrowExecuteException() {
        Long id = 1L;
        String name = "by banana";
        TravelType bananaWithoutId = TravelType.builder().name(name).build();
        when(travelTypeRepository.get(name)).thenReturn(Optional.empty());
        when(travelTypeRepository.update(id, bananaWithoutId)).then(invocation -> Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.builder().name(name).build();

        assertThrows(ExecuteException.class, () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void delete_existedTravelTypeThatIsContainedTour_shouldDeleteTravelType() {
        Long id = 1L;
        TravelType type = TravelType.builder().id(id).name("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(type))
                .thenReturn(Optional.empty());
        Tour emptyTour = Tour.builder().build();
        when(tourRepository.getByTravelTypeId(id)).thenReturn(Arrays.asList(emptyTour, emptyTour));

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, times(2)).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_existedTravelTypeThatIsNotContainedTour_shouldDeleteTravelType() {
        Long id = 1L;
        TravelType type = TravelType.builder().id(id).name("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(type)).thenReturn(Optional.empty());
        when(tourRepository.getByTravelTypeId(id)).thenReturn(Collections.emptyList());

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_notExistedTravelType_shouldThrowNoDataException() {
        Long id = 1L;
        when(travelTypeRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.delete(id));

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, never()).deleteByTourTypeId(id);
        verify(travelTypeRepository, never()).delete(id);
    }

    @Test
    void delete_existedTravelType_shouldThrowExecuteException() {
        Long id = 1L;
        TravelType type = TravelType.builder().id(id).name("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(type));

        assertThrows(ExecuteException.class, () -> travelTypeServiceImpl.delete(id));
    }
}