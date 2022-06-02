package com.github.legacycode.core;

import java.io.Serial;
import java.util.function.Function;

public class LegacyCodeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public static final Function<Class<?>, LegacyCodeException> PROVIDER_NOT_FOUND = (c) -> new LegacyCodeException("Missing provider for class: " + c);

    public LegacyCodeException() {
    }

    public LegacyCodeException(String message) {
        super(message);
    }

    public LegacyCodeException(Throwable cause) {
        super(cause);
    }

}
