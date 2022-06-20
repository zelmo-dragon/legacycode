package com.github.legacycode.core.gender;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.core.repository.Identifiable;
import com.github.legacycode.core.util.lang.Empty;
import com.github.legacycode.core.util.lang.Equals;
import com.github.legacycode.core.util.lang.ToString;
import com.github.legacycode.core.util.validation.Constraint;
import com.github.legacycode.core.util.validation.Validator;

public final class Gender implements Comparable<Gender>, Identifiable<UUID> {

    public static final Gender EMPTY = new Gender(Empty.EMPTY_UUID, Name.EMPTY, "");

    private final UUID id;
    private final Name name;
    private final String description;

    private Gender(final UUID id, final Name name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Gender of(final UUID id, final Name name, final String description) {
        return Validator
                .of(new Gender(id, name, description))
                .validate(Gender::getId, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Gender::getName, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Gender::getName, e -> !Objects.equals(e, Name.EMPTY), Constraint.MESSAGE_NOT_EMPTY_OBJECT)
                .validate(Gender::getDescription, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .get();
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
                .with("id", Gender::getId)
                .thenWith("name", Gender::getName)
                .thenWith("description", Gender::getDescription)
                .apply(this);
    }


    // Accesseurs

    @Override
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
