package com.project.hotels.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        String hotelId = "ABC123";
        String name = "Sample Hotel";
        String address = "AV 123";
        int numberOfRooms = 10;
        double rating = 4.5;

        Hotel hotel = new Hotel(id, hotelId, name, address, numberOfRooms, rating);

        assertAll(
                () -> assertEquals(id, hotel.getId(), "ID mismatch"),
                () -> assertEquals(hotelId, hotel.getHotelId(), "Hotel ID mismatch"),
                () -> assertEquals(name, hotel.getName(), "Name mismatch"),
                () -> assertEquals(address, hotel.getAddress(), "Address mismatch"),
                () -> assertEquals(numberOfRooms, hotel.getNumberOfRooms(), "Number of Rooms mismatch"),
                () -> assertEquals(rating, hotel.getRating(), 0.001, "Rating mismatch")
        );
    }

    @Test
    void testSetters() {
        Hotel hotel = new Hotel();

        hotel.setId(1L);
        hotel.setHotelId("ABC123");
        hotel.setName("Sample Hotel");
        hotel.setAddress("AV 123");
        hotel.setNumberOfRooms(10);
        hotel.setRating(4.5);

        assertAll(
                () -> assertEquals(1L, hotel.getId(), "Incorrect ID"),
                () -> assertEquals("ABC123", hotel.getHotelId(), "Incorrect Hotel ID"),
                () -> assertEquals("Sample Hotel", hotel.getName(), "Incorrect Name"),
                () -> assertEquals("AV 123", hotel.getAddress(), "Incorrect Address"),
                () -> assertEquals(10, hotel.getNumberOfRooms(), "Incorrect Number of Rooms"),
                () -> assertEquals(4.5, hotel.getRating(), 0.001, "Incorrect Rating")
        );
    }

}