package com.github.legacycode.core.customer;

import com.github.legacycode.core.Identifiable;

public record Customer(
        String givenName,
        String familyName,
        Email email,
        GenderId gender,
        String phoneNumber) implements Identifiable<Email> {

    @Override
    public Email getBusinessKey() {
        return this.email;
    }

}
