package com.project.hotels.infrastructure.adapters.controllers;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.hotels.application.dto.SearchResponseDTO;
import com.project.hotels.application.service.HotelServiceImpl;
import com.project.hotels.infrastructure.exceptions.HotelNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
class HotelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HotelServiceImpl hotelService;
    private static final String SEARCH_ENDPOINT = "/hotel/search";

    @Test
    void testSearchHotel_isSuccess() throws Exception {
        SearchResponseDTO mockResponse = new SearchResponseDTO("searchUUID");
        when(hotelService.searchHotel(any(SearchRequestDTO.class))).thenReturn(mockResponse);

        SearchRequestDTO requestDTO = new SearchRequestDTO(
                "sampleHotelId",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                Collections.singletonList(1),
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post(SEARCH_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.searchId").value("searchUUID"));
    }

    @Test
    void testSearchHotel_InvalidDateRangeException_badRequest() throws Exception {
        Map<String, Object> requestPayload = createRequestPayload("29/12/2025");

        mockMvc.perform(MockMvcRequestBuilders.post(SEARCH_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPayload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.errorMessage")
                        .value("check-in date must be before check-out date"));
    }

    @Test
    void testSearchHotel_ValueInstantiationException_badRequest() throws Exception {
        Map<String, Object> requestPayload = createRequestPayload(null);

        mockMvc.perform(MockMvcRequestBuilders.post(SEARCH_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPayload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.errorMessage")
                        .value("checkIn must not be null"));
    }

    @Test
    void testSearchHotel_HotelNotFoundException_badRequest() throws Exception {
        when(hotelService.searchHotel(any(SearchRequestDTO.class))).thenThrow(new HotelNotFoundException());

        Map<String, Object> requestPayload = createRequestPayload("28/12/2024");

        mockMvc.perform(MockMvcRequestBuilders.post(SEARCH_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPayload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.errorMessage")
                        .value("hotel not found"));
    }

    private Map<String, Object> createRequestPayload(String checkIn) {
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("hotelId", "sampleHotelId");
        requestPayload.put("checkIn", checkIn);
        requestPayload.put("checkOut", "29/12/2024");
        requestPayload.put("ages", Collections.singletonList(1));
        return requestPayload;
    }

}
