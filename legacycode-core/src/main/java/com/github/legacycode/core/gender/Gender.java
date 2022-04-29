package com.github.legacycode.core.gender;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.core.Identifiable;
import com.github.legacycode.util.Equals;
import com.github.legacycode.util.ToString;

public final class Gender implements Comparable<Gender>, Identifiable<UUID> {

    private final UUID id;
    private final Name name;
    private final String description;

    public Gender(final UUID id, final Name name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int compareTo(final Gender o) {
        return Comparator
                .comparing(Gender::getId)
                .thenComparing(Gender::getName)
                .thenComparing(Gender::getDescription)
                .compare(this, o);
    }

    @Override
    public boolean equals(final Object o) {
        return Equals
                .with(Gender::getId)
                .thenWith(Gender::getName)
                .thenWith(Gender::getDescription)
                .apply(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return ToString
                .with(Gender::getId)
                .thenWith(Gender::getName)
                .thenWith(Gender::getDescription)
                .apply(this);
    }

    @Override
    public UUID getKey() {
        return this.id;
    }

    // Accesseurs

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
