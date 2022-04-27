package com.github.legacycode.core.gender;

import com.github.legacycode.core.Identifiable;

public final class Gender implements Identifiable<Name> {
    private final Name name;

    private final String description;

    public Gender(Name name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Name getBusinessKey() {
        return this.name;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
