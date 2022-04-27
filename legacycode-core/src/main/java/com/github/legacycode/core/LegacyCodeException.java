package com.github.legacycode.core;

public class LegacyCodeException extends RuntimeException {

    public LegacyCodeException() {
    }

    public LegacyCodeException(String message) {
        super(message);
    }

    public LegacyCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LegacyCodeException(Throwable cause) {
        super(cause);
    }

}
