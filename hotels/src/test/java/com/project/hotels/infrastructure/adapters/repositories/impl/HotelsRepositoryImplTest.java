package com.project.hotels.infrastructure.adapters.repositories.impl;

import com.project.hotels.domain.Hotel;
import com.project.hotels.infrastructure.adapters.repositories.SpringDataJpaHotelsRepository;
import com.project.hotels.infrastructure.exceptions.HotelNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelsRepositoryImplTest {

    public static final String EMPTY = "";
    @Mock
    private SpringDataJpaHotelsRepository springDataJpaHotelsRepository;

    @InjectMocks
    private HotelsRepositoryImpl hotelsRepository;

    @Test
    void testValidateExistentHotel() {
        String hotelId = "sampleHotelId";
        Hotel mockHotel = new Hotel(1L, EMPTY, EMPTY, EMPTY, 1, 1D);

        when(springDataJpaHotelsRepository.findHotelByHotelId(hotelId)).thenReturn(Optional.of(mockHotel));

        Hotel result = hotelsRepository.validateExistentHotel(hotelId);

        assertAll(
                () -> verify(springDataJpaHotelsRepository).findHotelByHotelId(hotelId),
                () -> assertEquals(mockHotel, result));
    }

    @Test
    void testValidateExistentHotel_NotFound() {
        String hotelId = "nonExistentHotelId";

        when(springDataJpaHotelsRepository.findHotelByHotelId(hotelId)).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(HotelNotFoundException.class, () -> hotelsRepository.validateExistentHotel(hotelId)),
                () -> verify(springDataJpaHotelsRepository).findHotelByHotelId(hotelId));
    }

}