package com.project.search.domain.ports;

import com.project.search.domain.Search;

import java.time.LocalDate;
import java.util.Optional;

public interface SearchRepository {

    Optional<Search> findBySearchId(String searchId);

    long countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(LocalDate checkIn, LocalDate checkOut, int agesSize,
                                                                String searchId);

    void save(Search search);
}
