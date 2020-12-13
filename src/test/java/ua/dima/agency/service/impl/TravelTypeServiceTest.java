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
    void getById_existedTravelTypeId_shouldReturnTravelType() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(id))
                .then(invocation -> Optional.of(TravelType.create()
                        .withId(invocation.getArgument(0))
                        .withType(type).build())
                );

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(1L);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(type, receivedTravelTypeDto.getType());
    }

    @Test
    void getById_notExistedTravelTypeId_shouldThrowException() {
        Long id = 1L;
        when(travelTypeRepository.get(id)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(id));
    }

    @Test
    void getByType_existedTravelType_shouldReturnTravelType() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(type))
                .then(invocation -> Optional.of(TravelType.create()
                        .withId(id)
                        .withType(invocation.getArgument(0)).build())
                );

        TravelTypeDto receivedTravelTypeDto = travelTypeServiceImpl.get(type);

        assertNotNull(receivedTravelTypeDto);
        assertEquals(id, receivedTravelTypeDto.getId());
        assertEquals(type, receivedTravelTypeDto.getType());
    }

    @Test
    void getByType_notExistedTravelType_shouldThrowException() {
        String type = "by magic carpet";
        when(travelTypeRepository.get(type)).thenReturn(Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.get(type));
    }

    @Test
    void getAll_Nothing_shouldReturnAllTravelTypes() {
        TravelType tt = TravelType.create().withId(1L).withType("by magic carpet").build();
        TravelType tt1 = TravelType.create().withId(2L).withType("by banana").build();
        when(travelTypeRepository.getAll()).thenReturn(Arrays.asList(tt, tt1));

        List<TravelTypeDto> travelTypesDto = travelTypeServiceImpl.getAll();

        assertFalse(travelTypesDto.isEmpty());
        assertEquals(2, travelTypesDto.size());
        assertEquals(tt.getId(), travelTypesDto.get(0).getId());
        assertEquals(tt.getType(), travelTypesDto.get(0).getType());
        assertEquals(tt1.getId(), travelTypesDto.get(1).getId());
        assertEquals(tt1.getType(), travelTypesDto.get(1).getType());
    }

    @Test
    void getAll_Nothing_shouldReturnException() {
        when(travelTypeRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.getAll());
    }

    @Test
    void create_TravelTypeDto_shouldReturnNewTravelType() {
        Long id = 1L;
        String type = "by magic carpet";
        TravelType travelType = TravelType.create().withType(type).build();
        when(travelTypeRepository.create(travelType))
                .then(invocation -> (Optional.of(TravelType.create()
                        .withId(id)
                        .withType(((TravelType) invocation.getArgument(0)).getType())
                        .build()))
                );

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();
        TravelTypeDto createdTravelTypeDto = travelTypeServiceImpl.create(travelTypeDto);

        assertNotNull(createdTravelTypeDto);
        assertNotNull(createdTravelTypeDto.getId());
        assertEquals(type, createdTravelTypeDto.getType());
    }

    @Test
    void create_AlreadyExistedTravelTypeDto_shouldThrowException() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(type))
                .then(invocation -> (Optional.of(TravelType.create()
                        .withId(id)
                        .withType(invocation.getArgument(0))
                        .build()))
                );

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(ExtraDataException.class,
                () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void create_TravelTypeDto_shouldThrowException() {
        String type = "by magic carpet";
        TravelType travelType = TravelType.create().withType(type).build();
        when(travelTypeRepository.create(travelType))
                .then(invocation -> Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(SQLException.class,
                () -> travelTypeServiceImpl.create(travelTypeDto));
    }

    @Test
    void update_TravelTypeDto_shouldReturnUpdatedTravelType() {
        Long id = 1L;
        String type = "by magic carpet";
        TravelType travelType = TravelType.create().withType(type).build();
        when(travelTypeRepository.update(id, travelType))
                .then(invocation -> (Optional.of(TravelType.create()
                        .withId(invocation.getArgument(0))
                        .withType(((TravelType) invocation.getArgument(1)).getType())
                        .build()))
                );

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();
        TravelTypeDto updatedTravelTypeDto = travelTypeServiceImpl.update(id, travelTypeDto);

        assertNotNull(updatedTravelTypeDto);
        assertNotNull(updatedTravelTypeDto.getId());
        assertEquals(id, updatedTravelTypeDto.getId());
        assertEquals(type, updatedTravelTypeDto.getType());
    }

    @Test
    void update_AlreadyExistedTravelTypeDto_shouldThrowException() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(type))
                .then(invocation -> (Optional.of(TravelType.create()
                        .withId(id)
                        .withType(invocation.getArgument(0))
                        .build()))
                );

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(ExtraDataException.class,
                () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void update_TravelTypeDto_shouldThrowException() {
        Long id = 1L;
        String type = "by magic carpet";
        TravelType travelType = TravelType.create().withType(type).build();
        when(travelTypeRepository.update(id, travelType))
                .then(invocation -> Optional.empty());

        TravelTypeDto travelTypeDto = TravelTypeDto.create().withType(type).build();

        assertThrows(SQLException.class,
                () -> travelTypeServiceImpl.update(id, travelTypeDto));
    }

    @Test
    void delete_usedByTourTravelTypeDtoId_shouldExecuteDeleteMethods() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(id))
                .then(invocation -> Optional.of(TravelType.create()
                        .withId(invocation.getArgument(0))
                        .withType(type).build())
                );
        when(tourRepository.getByTravelTypeId(id))
                .then(invocation -> Arrays.asList(
                        Tour.create().build(),
                        Tour.create().build())
                );

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, times(2)).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_unusedByTourTravelTypeDtoId_shouldExecuteDeleteMethods() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(id))
                .then(invocation -> Optional.of(TravelType.create()
                        .withId(invocation.getArgument(0))
                        .withType(type).build())
                );
        when(tourRepository.getByTravelTypeId(id))
                .then(invocation -> Collections.emptyList());

        travelTypeServiceImpl.delete(id);

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, times(1)).deleteByTourTypeId(id);
        verify(travelTypeRepository, times(1)).delete(id);
    }

    @Test
    void delete_AlreadyExistedTravelTypeDtoId_shouldThrowException() {
        Long id = 1L;
        when(travelTypeRepository.get(id))
                .then(invocation -> Optional.empty());

        assertThrows(NoDataException.class, () -> travelTypeServiceImpl.delete(id));

        verify(countryTourRepository, never()).deleteByTourId(any());
        verify(tourRepository, never()).deleteByTourTypeId(id);
        verify(travelTypeRepository, never()).delete(id);
    }

    @Test
    void delete_TravelTypeDto_shouldThrowException() {
        Long id = 1L;
        String type = "by magic carpet";
        when(travelTypeRepository.get(id))
                .then(invocation -> Optional.of(TravelType.create()
                        .withId(invocation.getArgument(0))
                        .withType(type).build())
                );
        doThrow(new SQLException("")).when(travelTypeRepository).delete(id);

        assertThrows(SQLException.class, () -> travelTypeServiceImpl.delete(id));
    }
}