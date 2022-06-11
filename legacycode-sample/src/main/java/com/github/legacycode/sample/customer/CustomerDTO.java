package com.github.legacycode.sample.customer;

import java.util.UUID;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.github.legacycode.sample.gender.GenderDTO;


@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@XmlRootElement
public class CustomerDTO {

    @JsonbProperty("id")
    @XmlElement(name = "id")
    private UUID id;

    @JsonbProperty("givenName")
    @XmlElement(name = "givenName")
    private String givenName;

    @JsonbProperty("familyName")
    @XmlElement(name = "familyName")
    private String familyName;

    @JsonbProperty("email")
    @XmlElement(name = "email")
    private String email;

    @JsonbProperty("phoneNumber")
    @XmlElement(name = "phoneNumber")
    private String phoneNumber;

    @JsonbProperty("gender")
    @XmlElement(name = "gender")
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
