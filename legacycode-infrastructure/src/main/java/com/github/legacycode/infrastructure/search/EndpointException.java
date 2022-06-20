package com.github.legacycode.infrastructure.search;

import java.io.Serial;

public class EndpointException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EndpointException(final String message) {
        super(message);
    }
}
