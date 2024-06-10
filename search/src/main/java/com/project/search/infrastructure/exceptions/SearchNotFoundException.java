package com.project.search.infrastructure.exceptions;

public class SearchNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "search not found";

    public SearchNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
