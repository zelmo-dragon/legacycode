package com.github.legacycode.core.customer;

import java.util.Objects;
import java.util.UUID;

import com.github.legacycode.common.lang.Empty;
import com.github.legacycode.common.lang.Equals;
import com.github.legacycode.common.lang.ToString;
import com.github.legacycode.common.validation.Constraint;
import com.github.legacycode.common.validation.Validator;
import com.github.legacycode.core.Identifiable;

public final class Customer implements Identifiable<UUID> {

    public static final Customer EMPTY = new Customer(Empty.EMPTY_UUID, "", "", "", Email.EMPTY, GenderId.EMPTY);

    private final UUID id;
    private final String givenName;
    private final String familyName;
    private final String phoneNumber;
    private final Email email;
    private final GenderId gender;

    private Customer(
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

    public static Customer of(
            final UUID id,
            final String givenName,
            final String familyName,
            final String phoneNumber,
            final Email email,
            final GenderId gender) {

        return Validator
                .of(new Customer(id, givenName, familyName, phoneNumber, email, gender))
                .validate(Customer::getId, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Customer::getGivenName, Constraint::notEmpty, Constraint.MESSAGE_NOT_EMPTY)
                .validate(Customer::getFamilyName, Constraint::notEmpty, Constraint.MESSAGE_NOT_EMPTY)
                .validate(Customer::getPhoneNumber, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Customer::getEmail, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Customer::getEmail, e -> !Objects.equals(e, Email.EMPTY), Constraint.MESSAGE_NOT_EMPTY_OBJECT)
                .validate(Customer::getGender, Objects::nonNull, Constraint.MESSAGE_NOT_NULL)
                .validate(Customer::getGender, e -> !Objects.equals(e, GenderId.EMPTY), Constraint.MESSAGE_NOT_EMPTY_OBJECT)
                .get();
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
                .with("id", Customer::getId)
                .thenWith("givenName", Customer::getGivenName)
                .thenWith("familyName", Customer::getFamilyName)
                .thenWith("phoneNumber", Customer::getPhoneNumber)
                .thenWith("email", Customer::getEmail)
                .thenWith("gender", Customer::getGender)
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
