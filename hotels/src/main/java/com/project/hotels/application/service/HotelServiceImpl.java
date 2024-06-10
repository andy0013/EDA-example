package com.project.hotels.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.hotels.application.dto.SearchResponseDTO;
import com.project.hotels.domain.service.HotelService;
import com.project.hotels.infrastructure.adapters.repositories.impl.HotelsRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    private final KafkaProducerService kafkaProducerService;
    private final HotelsRepositoryImpl hotelsRepository;
    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public HotelServiceImpl(KafkaProducerService kafkaProducerService, HotelsRepositoryImpl hotelsRepository) {
        this.kafkaProducerService = kafkaProducerService;
        this.hotelsRepository = hotelsRepository;
    }

    @Override
    public SearchResponseDTO searchHotel(SearchRequestDTO searchRequestDTO) {
        this.hotelsRepository.validateExistentHotel(searchRequestDTO.hotelId());
        kafkaProducerService.sendMessage(topic, searchRequestDTO);
        return new SearchResponseDTO(searchRequestDTO.searchId());
    }
}
