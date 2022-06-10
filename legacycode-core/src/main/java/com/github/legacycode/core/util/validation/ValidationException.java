package com.github.legacycode.core.util.validation;

import java.io.Serial;

public final class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    ValidationException(final Class<?> targetClass) {
        super("Validation fail for object: " + targetClass.getName());
    }

    ValidationException(final String message) {
        super(message);
    }

}
