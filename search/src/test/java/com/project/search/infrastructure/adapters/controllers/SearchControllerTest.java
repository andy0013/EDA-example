package com.project.search.infrastructure.adapters.controllers;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.search.application.dto.CountResponseDTO;
import com.project.search.application.dto.SearchIdentifierDTO;
import com.project.search.application.service.SearchServiceImpl;
import com.project.search.infrastructure.exceptions.SearchNotFoundException;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SearchServiceImpl searchService;

    @Test
    void testSearchHotel() throws Exception {
        SearchRequestDTO requestDTO = createSampleSearchRequestDTO();
        CountResponseDTO mockResponse = new CountResponseDTO("sampleSearchIdE", requestDTO, 2L);

        when(searchService.searchHotel(any(SearchIdentifierDTO.class))).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/search/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SearchIdentifierDTO("searchId123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2L))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        CountResponseDTO countResponseDTO = objectMapper.readValue(responseContent, CountResponseDTO.class);
        assertAll(
                () -> assertEquals(mockResponse.searchId(), countResponseDTO.searchId(), "SearchId"),
                () -> assertEquals(mockResponse.search(), countResponseDTO.search(), "RequestDTO"),
                () -> assertEquals(mockResponse.count(), countResponseDTO.count(), "Count")
        );
    }

    @Test
    void testSearchHotel_badRequest() throws Exception {
        when(searchService.searchHotel(any(SearchIdentifierDTO.class)))
                .thenThrow(new SearchNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/search/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SearchIdentifierDTO("searchId123"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.errorMessage").value("search not found"));
    }

    private SearchRequestDTO createSampleSearchRequestDTO() {
        return new SearchRequestDTO(
                "sampleHotelId",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                Collections.singletonList(1),
                null
        );
    }

}