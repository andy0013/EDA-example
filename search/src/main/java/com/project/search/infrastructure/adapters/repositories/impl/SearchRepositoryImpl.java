package com.project.search.infrastructure.adapters.repositories.impl;

import com.project.search.domain.Search;
import com.project.search.domain.ports.SearchRepository;
import com.project.search.infrastructure.adapters.repositories.SpringDataMongoDbSearchRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class SearchRepositoryImpl implements SearchRepository {
    private final SpringDataMongoDbSearchRepository springDataMongoDbSearchRepository;

    public SearchRepositoryImpl(SpringDataMongoDbSearchRepository springDataMongoDbSearchRepository) {
        this.springDataMongoDbSearchRepository = springDataMongoDbSearchRepository;
    }

    @Override
    public Optional<Search> findBySearchId(String searchId) {
        return this.springDataMongoDbSearchRepository.findBySearchId(searchId);
    }

    @Override
    public long countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(LocalDate checkIn, LocalDate checkOut,
                                                                       int agesSize, String searchId) {
        return this.springDataMongoDbSearchRepository.countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(checkIn,
                checkOut, agesSize, searchId);
    }

    @Override
    public void save(Search search) {
        this.springDataMongoDbSearchRepository.save(search);
    }
}
