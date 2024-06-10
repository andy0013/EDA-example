package com.project.hotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.hotels.infrastructure.adapters.controllers",
        "com.project.hotels.application.service", "com.project.hotels.infrastructure.exceptions",
        "com.project.hotels.infrastructure.adapters.repositories.impl"})
public class HotelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelsApplication.class, args);
    }

}
