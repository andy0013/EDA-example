package com.project.hotels.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.hotels.application.dto.SearchResponseDTO;
import com.project.hotels.domain.Hotel;
import com.project.hotels.infrastructure.adapters.repositories.impl.HotelsRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    public static final String EMPTY = "";
    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private HotelsRepositoryImpl hotelsRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void testSearchHotel() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO("sampleHotelId",
                LocalDate.now(), LocalDate.now(), Collections.singletonList(1), "searchUUID");
        Hotel mockHotel = new Hotel(1L, EMPTY, EMPTY, EMPTY, 1, 1D);

        when(hotelsRepository.validateExistentHotel(searchRequestDTO.hotelId())).thenReturn(mockHotel);
        ReflectionTestUtils.setField(hotelService, "topic", "test-topic");
        SearchResponseDTO result = hotelService.searchHotel(searchRequestDTO);

        assertAll(
                () -> verify(hotelsRepository).validateExistentHotel(searchRequestDTO.hotelId()),
                () -> verify(kafkaProducerService).sendMessage(eq("test-topic"), eq(searchRequestDTO)),
                () -> assertEquals("searchUUID", result.searchId())
        );
    }

}