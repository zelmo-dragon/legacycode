package com.github.legacycode.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Validator<E> {

    private final E element;

    private final List<ValidationException> exceptions;

    private Validator(final E element) {
        this.element = element;
        this.exceptions = new ArrayList<>();
    }

    public static <E> Validator<E> of(final E element) {
        return new Validator<>(Objects.requireNonNull(element));
    }

    public Validator<E> validate(
            final Predicate<E> validation,
            final String message) {

        if (!validation.test(element)) {
            exceptions.add(new ValidationException(message));
        }
        return this;
    }

    public <U> Validator<E> validate(
            final Function<E, U> getter,
            final Predicate<U> validation,
            final String message) {

        return validate(getter.andThen(validation::test)::apply, message);
    }

    public E get() {
        if (!exceptions.isEmpty()) {
            ValidationException ex = new ValidationException(element.getClass());
            exceptions.forEach(ex::addSuppressed);
            throw ex;
        }
        return element;
    }

}
