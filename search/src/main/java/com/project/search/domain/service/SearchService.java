package com.project.search.domain.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.search.application.dto.CountResponseDTO;
import com.project.search.application.dto.SearchIdentifierDTO;

public interface SearchService {

    public CountResponseDTO searchHotel(SearchIdentifierDTO searchRequestDTO);

    public void saveSearch(SearchRequestDTO searchRequestDTO);

}
