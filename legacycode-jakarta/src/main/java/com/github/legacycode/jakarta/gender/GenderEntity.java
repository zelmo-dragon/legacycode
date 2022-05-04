package com.github.legacycode.jakarta.gender;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.github.legacycode.jakarta.AbstractEntity;

@Entity
@Table(name = "gender")
@Access(AccessType.FIELD)
public class GenderEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    public GenderEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
