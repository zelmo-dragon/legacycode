package com.github.legacycode.common.validation;

public final class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    ValidationException(final Class<?> targetClass) {
        super("Validation fail for object: " + targetClass.getName());
    }

    ValidationException(final String message) {
        super(message);
    }

}
