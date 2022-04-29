package com.github.legacycode.core.customer;

import java.util.Objects;
import java.util.UUID;

public final class GenderId {

    private final UUID id;

    public GenderId(final UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        boolean equality;

        if (this == o) {
            equality = true;
        } else if (o == null || getClass() != o.getClass()) {
            equality = false;
        } else {
            var name = (GenderId) o;
            equality = Objects.equals(id, name.id);
        }
        return equality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Accesseurs

    public UUID getId() {
        return id;
    }

}
