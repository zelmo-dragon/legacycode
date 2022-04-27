package com.github.legacycode.core.customer;

import com.github.legacycode.core.Identifiable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Customer implements Identifiable<Email> {

    String givenName;

    String familyName;

    String phoneNumber;

    Email email;

    GenderId gender;

    @Override
    public Email getBusinessKey() {
        return this.email;
    }

}
