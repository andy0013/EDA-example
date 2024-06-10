package com.project.hotels.domain.service;


import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.hotels.application.dto.SearchResponseDTO;

public interface HotelService {

    public SearchResponseDTO searchHotel(SearchRequestDTO searchRequestDTO);

}
