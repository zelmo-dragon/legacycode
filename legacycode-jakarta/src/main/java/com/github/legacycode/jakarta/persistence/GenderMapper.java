package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import jakarta.enterprise.context.Dependent;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.Name;

@Dependent
public class GenderMapper implements EntityMapper<GenderEntity, Gender>, Serializable {

    public GenderMapper() {
    }

    @Override
    public GenderEntity toEntity(Gender data) {
        var entity = new GenderEntity();
        this.updateEntity(data, entity);
        return entity;
    }

    @Override
    public Gender fromEntity(GenderEntity entity) {

        return new Gender(
                new Name(entity.getName()),
                entity.getDescription()
        );
    }

    @Override
    public void updateEntity(Gender data, GenderEntity entity) {
        entity.setName(data.name().value());
        entity.setDescription(data.description());
    }
}
