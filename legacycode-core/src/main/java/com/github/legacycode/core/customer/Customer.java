package com.github.legacycode.core.customer;

import com.github.legacycode.core.Identifiable;

public final class Customer implements Identifiable<Email> {

    private final String givenName;

    private final String familyName;

    private final String phoneNumber;

    private final Email email;

    private final GenderId gender;

    public Customer(String givenName, String familyName, String phoneNumber, Email email, GenderId gender) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }

    @Override
    public Email getBusinessKey() {
        return this.email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Email getEmail() {
        return email;
    }

    public GenderId getGender() {
        return gender;
    }
}
