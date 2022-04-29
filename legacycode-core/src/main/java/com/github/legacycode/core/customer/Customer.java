package com.github.legacycode.core.customer;

import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.core.Identifiable;

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
        boolean equality;
        if (this == o) {
            equality = true;
        } else if (o == null || getClass() != o.getClass()) {
            equality = false;
        } else {
            var other = (Customer) o;
            equality = Objects.equals(id, other.id)
                    && Objects.equals(givenName, other.givenName)
                    && Objects.equals(familyName, other.familyName)
                    && Objects.equals(phoneNumber, other.phoneNumber)
                    && Objects.equals(email, other.email)
                    && Objects.equals(gender, other.gender);
        }
        return equality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, phoneNumber, email, gender);
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
