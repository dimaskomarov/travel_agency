package ua.dima.agency.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.domain.TravelType;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
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
    void getById_existedTravelTypeDto_shouldReturnTravelTypeDto() {
        Long id = 1L;
        String type = "by banana";
        TravelType banana = TravelType.create().withId(id).withType(type).build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(banana));

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(id);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(type, receivedTravelTypeDto.getType());
    }

    @Test
    void getById_notExistedTravelTypeDto_shouldThrowNoDataException() {
        Long id = 1L;
        when(travelTypeRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(id));
    }

    @Test
    void getByType_existedTravelTypeDto_shouldReturnTravelTypeDto() {
        Long id = 1L;
        String type = "by banana";
        TravelType banana = TravelType.create().withId(id).withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.of(banana));

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(type);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(type, receivedTravelTypeDto.getType());
    }

    @Test
    void getByType_notExistedTravelTypeDto_shouldThrowNoDataException() {
        String type = "by banana";
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(type));
    }

    @Test
    void getAll_nothing_shouldReturnTwoTravelTypesDto() {
        Long carpetId = 1L;
        String carpetType = "by magic carpet";
        TravelType magicCarpet = TravelType.create().withId(carpetId).withType(carpetType).build();
        Long bananaId = 2L;
        String bananaType = "by banana";
        TravelType banana = TravelType.create().withId(bananaId).withType(bananaType).build();
        when(travelTypeRepository.getAll()).thenReturn(Arrays.asList(magicCarpet, banana));

        List<TravelTypeDto> travelTypesDto = travelTypeServiceImpl.getAll();

        assertFalse(travelTypesDto.isEmpty());
        assertEquals(2, travelTypesDto.size());
        assertEquals(carpetId, travelTypesDto.get(0).getId());
        assertEquals(carpetType, travelTypesDto.get(0).getType());
        assertEquals(bananaId, travelTypesDto.get(1).getId());
        assertEquals(bananaType, travelTypesDto.get(1).getType());
    }

    @Test
    void getAll_nothing_shouldThrowNoDataException() {
        when(travelTypeRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.getAll());
    }

    @Test
    void create_newTravelTypeDto_shouldReturnNewTravelTypeDto() {
        String type = "by banana";
        TravelType bananaWithoutId = TravelType.create().withType(type).build();
        TravelType banana = TravelType.create().withId(1L).withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());
        when(travelTypeRepository.create(bananaWithoutId)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDtoWithoutId = TravelTypeDto.create().withType(type).build();
        TravelTypeDto createdTravelTypeDto = travelTypeServiceImpl.create(travelTypeDtoWithoutId);

        assertNotNull(createdTravelTypeDto);
        assertNotNull(createdTravelTypeDto.getId());
        assertEquals(type, createdTravelTypeDto.getType());
    }

    @Test
    void create_existedTravelTypeDto_shouldThrowExtraDataException() {
        String type = "by banana";
        TravelType banana = TravelType.create().withId(1L).withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(ExtraDataException.class, () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void create_newTravelTypeDto_shouldThrowSQLException() {
        String type = "by banana";
        TravelType bananaWithoutId = TravelType.create().withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());
        when(travelTypeRepository.create(bananaWithoutId)).thenReturn(Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(SQLException.class, () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void update_newTravelTypeDto_shouldReturnUpdatedTravelTypeDto() {
        Long id = 1L;
        String type = "by banana";
        TravelType bananaWithoutId = TravelType.create().withType(type).build();
        TravelType banana = TravelType.create().withId(id).withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());
        when(travelTypeRepository.update(id, bananaWithoutId)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();
        TravelTypeDto updatedTravelTypeDto = travelTypeServiceImpl.update(id, travelTypeDto);

        assertNotNull(updatedTravelTypeDto);
        assertNotNull(updatedTravelTypeDto.getId());
        assertEquals(id, updatedTravelTypeDto.getId());
        assertEquals(type, updatedTravelTypeDto.getType());
    }

    @Test
    void update_existedTravelTypeDto_shouldThrowExtraDataException() {
        Long id = 1L;
        String type = "by banana";
        TravelType banana = TravelType.create().withId(id).withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.of(banana));

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(ExtraDataException.class, () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void update_newTravelTypeDto_shouldThrowSQLException() {
        Long id = 1L;
        String type = "by banana";
        TravelType bananaWithoutId = TravelType.create().withType(type).build();
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());
        when(travelTypeRepository.update(id, bananaWithoutId)).then(invocation -> Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(SQLException.class, () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void delete_existedTravelTypeDtoThatIsContainedTourDto_shouldDeleteTravelTypeDto() {
        Long id = 1L;
        TravelType banana = TravelType.create().withId(id).withType("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(banana));
        Tour emptyTour = Tour.create().build();
        when(tourRepository.getByTravelTypeId(id)).thenReturn(Arrays.asList(emptyTour, emptyTour));

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, times(2)).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_existedTravelTypeDtoThatIsNotContainedTourDto_shouldDeleteTravelTypeDto() {
        Long id = 1L;
        TravelType banana = TravelType.create().withId(id).withType("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(banana));
        when(tourRepository.getByTravelTypeId(id)).thenReturn(Collections.emptyList());

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_notExistedTravelTypeDto_shouldThrowNoDataException() {
        Long id = 1L;
        when(travelTypeRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.delete(id));

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, never()).deleteByTourTypeId(id);
        verify(travelTypeRepository, never()).delete(id);
    }

    @Test
    void delete_existedTravelTypeDto_shouldThrowSQLException() {
        Long id = 1L;
        TravelType banana = TravelType.create().withId(id).withType("by banana").build();
        when(travelTypeRepository.get(id)).thenReturn(Optional.of(banana));
        doThrow(new SQLException("")).when(travelTypeRepository).delete(id);

        assertThrows(SQLException.class, () -> travelTypeServiceImpl.delete(id));
    }
}