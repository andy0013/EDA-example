package com.project.search.infrastructure.adapters.controllers;

import com.project.search.application.dto.CountResponseDTO;
import com.project.search.application.dto.SearchIdentifierDTO;
import com.project.search.application.service.SearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchServiceImpl searchService;

    public SearchController(SearchServiceImpl kafkaProducerService) {
        this.searchService = kafkaProducerService;
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDTO> searchHotel(@RequestBody SearchIdentifierDTO search) {
        return ResponseEntity.ok().body(this.searchService.searchHotel(search));
    }
}
