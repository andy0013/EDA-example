package com.project.hotels.domain.ports;

import com.project.hotels.domain.Hotel;

public interface HotelsRepository {

    public Hotel validateExistentHotel(String hotelId);
}
