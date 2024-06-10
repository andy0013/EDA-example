package com.project.search.infrastructure.adapters.repositories;

import com.project.search.domain.Search;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SpringDataMongoDbSearchRepository extends MongoRepository<Search, String> {
    Optional<Search> findBySearchId(String searchId);

    @Query(value = "{ 'checkIn' : ?0, 'checkOut' : ?1, 'ages' : { '$size' : ?2 }, 'searchId' : { '$ne' : ?3 } }",
            count = true)
    long countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(LocalDate checkIn, LocalDate checkOut, int agesSize,
                                                                String searchId);
}
