package com.github.legacycode.core;

import java.util.function.Function;

public class LegacyCodeException extends RuntimeException {

    public static Function<Class<?>, LegacyCodeException> PROVIDER_NOT_FOUND = (c) -> new LegacyCodeException("Missing provider for class: " + c);

    public LegacyCodeException() {
    }

    public LegacyCodeException(String message) {
        super(message);
    }

    public LegacyCodeException(Throwable cause) {
        super(cause);
    }

}
