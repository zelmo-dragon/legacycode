package com.github.legacycode.sample.gender;

import java.util.Objects;
import java.util.UUID;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.github.legacycode.infrastructure.persistence.AbstractEntity;

@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@XmlRootElement
public class GenderDTO {

    @JsonbProperty("id")
    @XmlElement(name = "id")
    private UUID id;

    @JsonbProperty("name")
    @XmlElement(name = "name")
    private String name;

    @JsonbProperty("code")
    @XmlElement(name = "code")
    private String code;

    @JsonbProperty("description")
    @XmlElement(name = "description")
    private String description;

    public GenderDTO() {
    }

    @Override
    public boolean equals(Object o) {
        boolean eq;
        if (this == o) {
            eq = true;
        } else if (o == null || getClass() != o.getClass()) {
            eq = false;
        } else {
            var that = (GenderDTO) o;
            eq = Objects.equals(id, that.id)
                    && Objects.equals(name, that.name)
                    && Objects.equals(code, that.code)
                    && Objects.equals(description, that.description) ;
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, description);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
