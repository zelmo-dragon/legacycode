package com.github.legacycode.core.gender;

import java.util.Comparator;
import java.util.Objects;

import com.github.legacycode.core.util.lang.Equals;
import com.github.legacycode.core.util.lang.ToString;
import com.github.legacycode.core.util.validation.Constraint;
import com.github.legacycode.core.util.validation.Validator;


public final class Name implements Comparable<Name> {

    public static final Name EMPTY = new Name("");

    private final String value;

    private Name(final String value) {
        this.value = value;
    }

    public static Name of(final String value) {
        return Validator
                .of(new Name(value))
                .validate(Name::getValue, Constraint::notEmpty, Constraint.MESSAGE_NOT_EMPTY)
                .get();
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
                .with("value", Name::getValue)
                .apply(this);
    }

    // Accesseurs

    public String getValue() {
        return value;
    }
}
