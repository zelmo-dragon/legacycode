package com.github.legacycode.jakarta.persistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "customer")
@Access(AccessType.FIELD)
public class CustomerEntity extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    @Column(name = "given_name", nullable = false)
    String givenName;

    @NotBlank
    @Size(max = 255)
    @Column(name = "family_name", nullable = false)
    String familyName;

    @NotBlank
    @Size(max = 255)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @NotNull
    @Column(name = "gender", nullable = false)
    GenderEntity gender;

    @NotBlank
    @Size(max = 255)
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

}
