package com.github.legacycode.core.customer;

import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.core.Identifiable;
import com.github.legacycode.util.Equals;
import com.github.legacycode.util.ToString;

public final class Customer implements Identifiable<UUID> {

    private final UUID id;
    private final String givenName;
    private final String familyName;
    private final String phoneNumber;
    private final Email email;
    private final GenderId gender;

    public Customer(
            final UUID id,
            final String givenName,
            final String familyName,
            final String phoneNumber,
            final Email email,
            final GenderId gender) {

        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        return Equals
                .with(Customer::getId)
                .thenWith(Customer::getGivenName)
                .thenWith(Customer::getFamilyName)
                .thenWith(Customer::getPhoneNumber)
                .thenWith(Customer::getEmail)
                .thenWith(Customer::getGender)
                .apply(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, phoneNumber, email, gender);
    }

    @Override
    public String toString() {
        return ToString
                .with(Customer::getId)
                .thenWith(Customer::getGivenName)
                .thenWith(Customer::getFamilyName)
                .thenWith(Customer::getPhoneNumber)
                .thenWith(Customer::getEmail)
                .thenWith(Customer::getGender)
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
