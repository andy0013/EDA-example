package com.project.hotels.application.service;

import com.project.core.library.infrastructure.kafka.SearchRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {
    @Mock
    private KafkaTemplate<String, SearchRequestDTO> kafkaTemplate;
    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    void testSendMessage() {
        String topic = "test-topic";
        SearchRequestDTO message = new SearchRequestDTO("SampleCity", LocalDate.now(),
                LocalDate.now(), Collections.singletonList(1), "searchUUID");

        kafkaProducerService.sendMessage(topic, message);

        verify(kafkaTemplate).send(eq(topic), eq(message));
    }
}