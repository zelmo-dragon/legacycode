package com.github.legacycode.core.customer;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.common.lang.Empty;
import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;
import com.github.legacycode.common.validation.Constraint;
import com.github.legacycode.common.validation.Validator;

public final class GenderId implements Comparable<GenderId> {

    public static final GenderId EMPTY = new GenderId(Empty.EMPTY_UUID);

    private final UUID id;

    private GenderId(final UUID id) {
        this.id = id;
    }

    public static GenderId of(final UUID id) {
        return Validator
                .of(new GenderId(id))
                .validate(GenderId::getId, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .get();
    }

    @Override
    public int compareTo(final GenderId o) {
        return Comparator
                .comparing(GenderId::getId)
                .compare(this, o);
    }

    @Override
    public boolean equals(final Object o) {
        return Equals
                .with(GenderId::getId)
                .apply(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return ToString
                .with("id", GenderId::getId)
                .apply(this);
    }

    // Accesseurs

    public UUID getId() {
        return id;
    }

}
