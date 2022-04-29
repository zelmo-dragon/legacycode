package com.github.legacycode.core.customer;

import java.util.Comparator;
import java.util.Objects;

import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;

public final class Email implements Comparable<Email> {

    public static final Email EMPTY = new Email("");

    private final String value;

    public Email(final String value) {
        this.value = value;
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
                .with(Email::getValue)
                .apply(this);
    }

    // Accesseurs

    public String getValue() {
        return value;
    }

}
