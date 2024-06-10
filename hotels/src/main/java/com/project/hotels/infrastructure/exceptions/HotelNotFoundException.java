package com.project.hotels.infrastructure.exceptions;

public class HotelNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "hotel not found";

    public HotelNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
