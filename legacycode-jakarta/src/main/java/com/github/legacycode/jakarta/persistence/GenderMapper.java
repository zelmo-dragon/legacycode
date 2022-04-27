package com.github.legacycode.jakarta.persistence;

import java.util.Optional;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.Name;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface GenderMapper extends EntityMapper<GenderEntity, Gender> {

    @Override
    GenderEntity toEntity(Gender data);

    @InheritInverseConfiguration(name = "toEntity")
    @Override
    Gender fromEntity(GenderEntity entity);

    @Override
    void updateEntity(Gender data, @MappingTarget GenderEntity entity);

    default String map(Name name) {
        return Optional
                .ofNullable(name)
                .map(Name::getValue)
                .orElse(null);
    }

    default Name map(String value) {
        return new Name(value);
    }
}
