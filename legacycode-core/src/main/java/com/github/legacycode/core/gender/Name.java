package com.github.legacycode.core.gender;

import java.util.Objects;

public final class Name {

    private final String value;

    public Name(final String value) {
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
            var name = (Name) o;
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
