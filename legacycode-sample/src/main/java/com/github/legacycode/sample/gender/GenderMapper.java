package com.github.legacycode.sample.gender;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.github.legacycode.infrastructure.search.EntityMapper;

@Singleton
public class GenderMapper implements EntityMapper<GenderEntity, GenderDTO> {

    private final GenderDAO dao;

    @Inject
    public GenderMapper(final GenderDAO dao) {
        this.dao = dao;
    }

    @Override
    public GenderEntity toEntity(final GenderDTO data) {
        var entity = this.dao
                .find(data.getId())
                .orElseGet(GenderEntity::new);

        this.updateEntity(data, entity);
        return entity;
    }

    @Override
    public GenderDTO fromEntity(final GenderEntity entity) {
        var data = new GenderDTO();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setCode(entity.getCode());
        data.setDescription(entity.getDescription());
        return data;
    }

    @Override
    public void updateEntity(final GenderDTO source, final GenderEntity target) {
        target.setName(source.getName());
        target.setCode(source.getCode());
        target.setDescription(source.getDescription());
    }
}
