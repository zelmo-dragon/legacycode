package com.github.legacycode.core.gender;

import com.github.legacycode.core.Identifiable;
import lombok.Value;

@Value
public class Gender implements Identifiable<Name> {
    Name name;

    String description;

    @Override
    public Name getBusinessKey() {
        return this.name;
    }


}
