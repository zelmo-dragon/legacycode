package com.github.legacycode.core.gender;

import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.core.Identifiable;

public final class Gender implements Identifiable<UUID> {

    private final UUID id;
    private final Name name;
    private final String description;

    public Gender(final UUID id, final Name name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        boolean equality;
        if (this == o) {
            equality = true;
        } else if (o == null || getClass() != o.getClass()) {
            equality = false;
        } else {
            var other = (Gender) o;
            equality = Objects.equals(id, other.id)
                    && Objects.equals(name, other.name)
                    && Objects.equals(description, other.description);
        }
        return equality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
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
