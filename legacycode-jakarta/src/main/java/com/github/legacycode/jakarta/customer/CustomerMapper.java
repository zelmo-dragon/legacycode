package com.github.legacycode.jakarta.customer;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.github.legacycode.endpoint.EntityMapper;

@Singleton
public class CustomerMapper implements EntityMapper<CustomerEntity, CustomerDTO> {

    private final CustomerDAO dao;

    @Inject
    public CustomerMapper(final CustomerDAO dao) {
        this.dao = dao;
    }

    @Override
    public CustomerEntity toEntity(final CustomerDTO data) {
        var entity = this.dao
                .find(data.getId())
                .orElseGet(CustomerEntity::new);

        this.updateEntity(data, entity);

        return entity;
    }

    @Override
    public CustomerDTO fromEntity(final CustomerEntity entity) {
        var data = new CustomerDTO();
        data.setGivenName(entity.getGivenName());
        data.setFamilyName(entity.getFamilyName());
        data.setEmail(entity.getEmail());
        data.setPhoneNumber(entity.getPhoneNumber());
        return data;
    }

    @Override
    public void updateEntity(final CustomerDTO source, final CustomerEntity target) {
        target.setGivenName(source.getGivenName());
        target.setFamilyName(source.getFamilyName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
    }
}
