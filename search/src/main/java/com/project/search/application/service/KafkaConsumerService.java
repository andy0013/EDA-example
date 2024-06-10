package com.project.search.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import com.project.search.domain.service.SearchService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final SearchService searchService;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    public KafkaConsumerService(SearchService searchService) {
        this.searchService = searchService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void consumeMessage(ConsumerRecord<String, SearchRequestDTO> record) {
        SearchRequestDTO message = record.value();
        this.searchService.saveSearch(message);
    }

}
