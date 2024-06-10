package com.project.search.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.search.application.dto.CountResponseDTO;
import com.project.search.application.dto.SearchIdentifierDTO;
import com.project.search.domain.Search;
import com.project.search.infrastructure.adapters.repositories.impl.SearchRepositoryImpl;
import com.project.search.infrastructure.exceptions.SearchNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    @Mock
    private SearchRepositoryImpl searchRepository;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    void testSearchHotel() {
        Search search = new Search(
                "searchId123",
                "hotelId123",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                Arrays.asList(25, 30, 35)
        );
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO(search.hotelId(),
                search.checkIn(), search.checkOut(), search.ages(), null);


        when(searchRepository.findBySearchId(anyString()))
                .thenReturn(Optional.of(search));
        when(searchRepository.countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(
                any(LocalDate.class), any(LocalDate.class), anyInt(), anyString()))
                .thenReturn(5L);

        CountResponseDTO countResponseDTO = searchService.searchHotel(new SearchIdentifierDTO("searchId123"));

        assertAll(
                () -> assertEquals("searchId123", countResponseDTO.searchId(), "SearchId"),
                () -> assertEquals(searchRequestDTO, countResponseDTO.search(), "RequestDTO"),
                () -> assertEquals(5L, countResponseDTO.count(), "Count"),
                () -> verify(searchRepository, times(1)).findBySearchId("searchId123"),
                () -> verify(searchRepository, times(1)).countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(
                        search.checkIn(), search.checkOut(), search.ages().size(), search.searchId())
        );
    }

    @Test
    void testSearchHotel_SearchNotFoundException() {
        SearchIdentifierDTO searchIdentifierDTO = new SearchIdentifierDTO("searchId123");

        when(searchRepository.findBySearchId(anyString()))
                .thenReturn(Optional.empty());
        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> {
            searchService.searchHotel(searchIdentifierDTO);
        });

        assertEquals("search not found", exception.getMessage());
    }

    @Test
    void testSaveSearch() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO(
                "hotelId123",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                Arrays.asList(25, 30, 35),
                "searchId123"
        );

        searchService.saveSearch(searchRequestDTO);

        verify(searchRepository, times(1)).save(any(Search.class));
    }
}