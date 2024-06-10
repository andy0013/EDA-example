package com.project.core.library.infrastructure.exceptions;

public class InvalidDateRangeException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "check-in date must be before check-out date";

    public InvalidDateRangeException() {
        super(DEFAULT_MESSAGE);
    }

}
