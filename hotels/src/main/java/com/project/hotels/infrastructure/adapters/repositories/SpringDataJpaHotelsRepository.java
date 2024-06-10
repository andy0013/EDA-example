package com.project.hotels.infrastructure.adapters.repositories;

import com.project.hotels.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJpaHotelsRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findHotelByHotelId(String hotelId);

}
