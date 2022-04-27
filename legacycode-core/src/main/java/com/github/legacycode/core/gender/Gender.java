package com.github.legacycode.core.gender;

import com.github.legacycode.core.Identifiable;

public record Gender(
        Name name, 
        String description) implements Identifiable<Name> {

    @Override
    public Name getBusinessKey() {
        return this.name;
    }

}
