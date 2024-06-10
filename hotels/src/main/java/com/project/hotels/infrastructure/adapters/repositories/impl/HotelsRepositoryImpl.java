package com.project.hotels.infrastructure.adapters.repositories.impl;

import com.project.hotels.domain.Hotel;
import com.project.hotels.domain.ports.HotelsRepository;
import com.project.hotels.infrastructure.adapters.repositories.SpringDataJpaHotelsRepository;
import com.project.hotels.infrastructure.exceptions.HotelNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class HotelsRepositoryImpl implements HotelsRepository {
    private final SpringDataJpaHotelsRepository springDataJpaHotelsRepository;

    public HotelsRepositoryImpl(SpringDataJpaHotelsRepository springDataJpaHotelsRepository) {
        this.springDataJpaHotelsRepository = springDataJpaHotelsRepository;
    }

    @Override
    public Hotel validateExistentHotel(String hotelId) {
        return this.springDataJpaHotelsRepository
                .findHotelByHotelId(hotelId)
                .orElseThrow(HotelNotFoundException::new);
    }

}
