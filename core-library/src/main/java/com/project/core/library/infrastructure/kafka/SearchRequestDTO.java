package com.project.core.library.infrastructure.kafka;

import com.project.core.library.infrastructure.exceptions.EmptyAgesException;
import com.project.core.library.infrastructure.exceptions.InvalidDateRangeException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchRequestDTO(String hotelId, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkIn,
                               @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkOut, List<Integer> ages,
                               String searchId) {

    public SearchRequestDTO {
        Objects.requireNonNull(hotelId, "hotelId must not be null");
        Objects.requireNonNull(checkIn, "checkIn must not be null");
        Objects.requireNonNull(checkOut, "checkOut must not be null");
        Objects.requireNonNull(ages, "ages must not be null");
        if (checkIn.isAfter(checkOut)) {
            throw new InvalidDateRangeException();
        }
        if (ages.isEmpty()) {
            throw new EmptyAgesException();
        }
    }

    public SearchRequestDTO withSearchId() {
        return new SearchRequestDTO(hotelId(), checkIn(), checkOut(), ages(), UUID.randomUUID().toString());
    }

}
