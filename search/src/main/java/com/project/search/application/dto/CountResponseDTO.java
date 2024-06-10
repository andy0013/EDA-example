package com.project.search.application.dto;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;

public record CountResponseDTO(String searchId, SearchRequestDTO search, Long count) {

}
