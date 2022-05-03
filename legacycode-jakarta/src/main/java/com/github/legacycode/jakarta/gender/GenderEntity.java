package com.github.legacycode.jakarta.gender;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.legacycode.jakarta.persistence.AbstractEntity;

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
