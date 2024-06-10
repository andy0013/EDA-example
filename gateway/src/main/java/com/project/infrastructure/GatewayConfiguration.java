package com.project.infrastructure;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("hotel-search", r -> r
                        .path("/hotel/*")
                        .uri("http://localhost:8081"))
                .route("search", r -> r
                        .path("/search/*")
                        .uri("http://localhost:8082"))
                .build();
    }

}
