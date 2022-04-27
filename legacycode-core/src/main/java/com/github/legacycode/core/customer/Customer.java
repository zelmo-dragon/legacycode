package com.github.legacycode.core.customer;

import com.github.legacycode.core.Identifiable;

public record Customer(
        String givenName,
        String familyName,
        String phoneNumber,
        Email email,
        GenderId gender) implements Identifiable<Email> {

    @Override
    public Email getBusinessKey() {
        return this.email;
    }

}
