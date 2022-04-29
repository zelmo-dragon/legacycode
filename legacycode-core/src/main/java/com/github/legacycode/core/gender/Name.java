package com.github.legacycode.core.gender;

import java.util.Comparator;
import java.util.Objects;

import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;

public final class Name implements Comparable<Name> {

    public static final Name EMPTY = new Name("");

    private final String value;

    public Name(final String value) {
        this.value = value;
    }

    @Override
    public int compareTo(final Name o) {
        return Comparator
                .comparing(Name::getValue)
                .compare(this, o);
    }

    @Override
    public boolean equals(final Object o) {
        return Equals
                .with(Name::getValue)
                .apply(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return ToString
                .with(Name::getValue)
                .apply(this);
    }

    // Accesseurs

    public String getValue() {
        return value;
    }
}
