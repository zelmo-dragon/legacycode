package com.github.legacycode.sample.customer;

import java.util.UUID;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;

import com.github.legacycode.sample.gender.GenderDTO;


@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
public class CustomerDTO {

    @JsonbProperty("id")
    private UUID id;

    @JsonbProperty("givenName")
    private String givenName;

    @JsonbProperty("familyName")
    private String familyName;

    @JsonbProperty("email")
    private String email;

    @JsonbProperty("phoneNumber")
    private String phoneNumber;

    @JsonbProperty("gender")
    private GenderDTO gender;

    public CustomerDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(final String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(final String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(final GenderDTO gender) {
        this.gender = gender;
    }
}
