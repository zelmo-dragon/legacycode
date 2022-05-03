package com.github.legacycode.jakarta.customer;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.legacycode.jakarta.persistence.AbstractEntity;
import com.github.legacycode.jakarta.gender.GenderEntity;

@Entity
@Table(name = "customer")
@Access(AccessType.FIELD)
public class CustomerEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    @Column(name = "given_name", nullable = false)
    private String givenName;

    @NotBlank
    @Size(max = 255)
    @Column(name = "family_name", nullable = false)
    private String familyName;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "gender", nullable = false)
    private GenderEntity gender;

    @NotBlank
    @Size(max = 255)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public CustomerEntity() {
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

    public GenderEntity getGender() {
        return gender;
    }

    public void setGender(final GenderEntity gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
