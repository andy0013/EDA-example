package com.project.search.infrastructure.exceptions;

import com.project.core.library.infrastructure.adapters.controllers.output.Header;
import com.project.core.library.infrastructure.adapters.controllers.output.Response;
import com.project.core.library.infrastructure.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(SearchNotFoundException.class)
    public ResponseEntity<Response<String>> handleSearchNotFoundExceptionException(SearchNotFoundException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response<String> stringResponse = new Response<>(Constants.ERROR, new Header(ex.getLocalizedMessage(),
                Constants.ERROR_SEARCH_NOT_FOUND));
        return ResponseEntity.status(status).body(stringResponse);
    }

}
