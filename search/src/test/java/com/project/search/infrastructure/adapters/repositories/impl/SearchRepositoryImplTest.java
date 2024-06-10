package com.project.search.infrastructure.adapters.repositories.impl;

import com.project.search.domain.Search;
import com.project.search.infrastructure.adapters.repositories.SpringDataMongoDbSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataMongoTest
public class SearchRepositoryImplTest {

    @Autowired
    private SearchRepositoryImpl searchRepositoryImpl;
    @MockBean
    private SpringDataMongoDbSearchRepository springDataMongoDbSearchRepository;

    @Test
    void testFindBySearchId() {
        String searchId = "searchId123";
        Search expectedSearch = new Search(searchId, "hotel123", LocalDate.now(), LocalDate.now().plusDays(1),
                Arrays.asList(25, 30, 35));

        when(springDataMongoDbSearchRepository.findBySearchId(searchId)).thenReturn(Optional.of(expectedSearch));

        Optional<Search> result = searchRepositoryImpl.findBySearchId(searchId);

        assertEquals(Optional.of(expectedSearch), result);
    }

    @Test
    void testCountByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId() {
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);
        int agesSize = 3;
        String searchId = "searchId123";
        long expectedCount = 5;

        when(springDataMongoDbSearchRepository.countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(checkIn,
                checkOut, agesSize, searchId)).thenReturn(expectedCount);

        long result = searchRepositoryImpl.countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(checkIn, checkOut,
                agesSize, searchId);

        assertEquals(expectedCount, result);
    }
}
