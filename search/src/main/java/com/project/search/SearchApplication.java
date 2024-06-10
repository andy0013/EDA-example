package com.project.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.search.infrastructure.adapters.controllers",
        "com.project.search.application.service", "com.project.search.infrastructure.exceptions",
        "com.project.search.infrastructure.adapters.repositories.impl"})
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
