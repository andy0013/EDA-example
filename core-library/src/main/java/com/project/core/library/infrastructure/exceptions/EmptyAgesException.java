package com.project.core.library.infrastructure.exceptions;

public class EmptyAgesException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "ages must not be empty";

    public EmptyAgesException() {
        super(DEFAULT_MESSAGE);
    }

}
