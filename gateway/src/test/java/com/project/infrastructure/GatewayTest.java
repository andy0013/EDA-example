package com.project.infrastructure;

import com.project.gateway.GatewayApplication;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = GatewayApplication.class,webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class GatewayTest {
    public static final String MOCKED_RESPONSE_MS_HOTELS = "Mocked Response MS-HOTELS";
    public static final String MOCKED_RESPONSE_MS_SEARCH = "Mocked Response MS-SEARCH";
    @Autowired
    private WebTestClient webTestClient;
    private MockWebServer mockWebServer8081;
    private MockWebServer mockWebServer8082;

    @BeforeEach
    void setup() throws Exception {
        mockWebServer8081 = new MockWebServer();
        mockWebServer8082 = new MockWebServer();

        mockWebServer8081.start(8081);
        mockWebServer8082.start(8082);

        mockWebServer8081.enqueue(new MockResponse().setBody(MOCKED_RESPONSE_MS_HOTELS));
        mockWebServer8082.enqueue(new MockResponse().setBody(MOCKED_RESPONSE_MS_SEARCH));
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer8081.shutdown();
        mockWebServer8082.shutdown();
    }


    @Test
    void testHotelRoute_isSuccess() {
        webTestClient
                .get()
                .uri("/hotel/sample-path")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(MOCKED_RESPONSE_MS_HOTELS);
    }

    @Test
    void testSearchRoute_isSuccess() {
        webTestClient
                .get()
                .uri("/search/sample-path")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(MOCKED_RESPONSE_MS_SEARCH);
    }


    @Test
    void testInvalidRoute() {
        webTestClient
                .get()
                .uri("/invalid-path")
                .exchange()
                .expectStatus().isNotFound();
    }
}