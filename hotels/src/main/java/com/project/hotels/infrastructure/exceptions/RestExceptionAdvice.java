package com.project.hotels.infrastructure.exceptions;

import com.project.core.library.infrastructure.adapters.controllers.output.Header;
import com.project.core.library.infrastructure.adapters.controllers.output.Response;
import com.project.core.library.infrastructure.util.Constants;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Response<String>> handleValueInstantiationException(ValueInstantiationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response<String> stringResponse = new Response<>(Constants.ERROR,
                new Header(ex.getCause().getLocalizedMessage(), Constants.ERROR_VALUE_INSTANTATION));
        return ResponseEntity.status(status)
                .body(stringResponse);
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Response<String>> handleHotelNotFoundException(HotelNotFoundException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response<String> stringResponse = new Response<>(Constants.ERROR,
                new Header(ex.getLocalizedMessage(), Constants.ERROR_HOTEL_NOT_FOUND));
        return ResponseEntity.status(status)
                .body(stringResponse);
    }


}
