package com.github.legacycode.sample.gender;

import java.util.UUID;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
