package com.github.legacycode.core.customer;

import java.util.Comparator;
import java.util.Objects;

import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;
import com.github.legacycode.common.validation.Constraint;
import com.github.legacycode.common.validation.Validator;

public final class Email implements Comparable<Email> {

    public static final Email EMPTY = new Email("");

    private final String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email of(final String value) {
        return Validator
                .of(new Email(value))
                .validate(Email::getValue, Constraint::isEmailValid, Constraint.MESSAGE_INVALID_EMAIL)
                .get();
    }

    @Override
    public int compareTo(final Email o) {
        return Comparator
                .comparing(Email::getValue)
                .compare(this, o);
    }

    @Override
    public boolean equals(final Object o) {
        return Equals
                .with(Email::getValue)
                .apply(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return ToString
                .with("value", Email::getValue)
                .apply(this);
    }

    // Accesseurs

    public String getValue() {
        return value;
    }

}
