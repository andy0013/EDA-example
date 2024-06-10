package com.project.search.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.search.application.dto.CountResponseDTO;
import com.project.search.application.dto.SearchIdentifierDTO;
import com.project.search.domain.Search;
import com.project.search.domain.service.SearchService;
import com.project.search.infrastructure.adapters.repositories.impl.SearchRepositoryImpl;
import com.project.search.infrastructure.exceptions.SearchNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepositoryImpl searchRepository;

    public SearchServiceImpl(SearchRepositoryImpl searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public CountResponseDTO searchHotel(SearchIdentifierDTO searchIdentifierDTO) {
        Search search =
                this.searchRepository.findBySearchId(searchIdentifierDTO.searchId()).orElseThrow(SearchNotFoundException::new);
        long similarSearchCount =
                this.searchRepository.countByCheckInAndCheckOutAndAgesSizeAndNotSameSearchId(search.checkIn(),
                        search.checkOut(), search.ages().size(), search.searchId());
        SearchRequestDTO requestDTO = new SearchRequestDTO(search.hotelId(), search.checkIn(), search.checkOut(),
                search.ages(), null);
        return new CountResponseDTO(searchIdentifierDTO.searchId(), requestDTO, similarSearchCount);
    }

    @Override
    public void saveSearch(SearchRequestDTO searchRequestDTO) {
        Search search = new Search(searchRequestDTO.searchId(), searchRequestDTO.hotelId(),
                searchRequestDTO.checkIn(), searchRequestDTO.checkOut(), searchRequestDTO.ages());
        this.searchRepository.save(search);
    }
}
