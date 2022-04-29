package com.github.legacycode.core.customer;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.common.lang.Empty;
import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;

public final class GenderId implements Comparable<GenderId> {

    public static final GenderId EMPTY = new GenderId(Empty.EMPTY_UUID);

    private final UUID id;

    public GenderId(final UUID id) {
        this.id = id;
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
                .with(GenderId::getId)
                .apply(this);
    }

    // Accesseurs

    public UUID getId() {
        return id;
    }

}
