package com.github.legacycode.core.customer;

import java.util.Objects;

import com.github.legacycode.core.gender.Name;

public final class Email {

    private final String value;

    public Email(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        boolean equality;

        if (this == o) {
            equality = true;
        } else if (o == null || getClass() != o.getClass()) {
            equality = false;
        } else {
            var name = (Email) o;
            equality = Objects.equals(value, name.value);
        }
        return equality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    // Accesseurs

    public String getValue() {
        return value;
    }

}
