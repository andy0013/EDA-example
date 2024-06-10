package com.project.hotels.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, SearchRequestDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, SearchRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, SearchRequestDTO message) {
        kafkaTemplate.send(topic, message);
    }
}
