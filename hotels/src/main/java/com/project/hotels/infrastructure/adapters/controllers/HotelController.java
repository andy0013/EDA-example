package com.project.hotels.infrastructure.adapters.controllers;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.hotels.application.dto.SearchResponseDTO;
import com.project.hotels.application.service.HotelServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelServiceImpl hotelService;

    public HotelController(HotelServiceImpl kafkaProducerService) {
        this.hotelService = kafkaProducerService;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponseDTO> searchHotel(@RequestBody SearchRequestDTO search) {
        SearchRequestDTO searchWithGeneratedId = search.withSearchId();
        return ResponseEntity.ok(this.hotelService.searchHotel(searchWithGeneratedId));
    }
}
