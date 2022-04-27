package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.Email;
import com.github.legacycode.core.customer.GenderId;

@Dependent
public class CustomerMapper implements EntityMapper<CustomerEntity, Customer>, Serializable {

    private final GenderMapper genderMapper;

    @Inject
    public CustomerMapper(GenderMapper genderMapper) {
        this.genderMapper = genderMapper;
    }

    @Override
    public CustomerEntity toEntity(Customer data) {
        var entity = new CustomerEntity();
        this.updateEntity(data, entity);
        return entity;
    }

    @Override
    public Customer fromEntity(CustomerEntity entity) {
        return new Customer(
                entity.getGivenName(),
                entity.getFamilyName(),
                entity.getPhoneNumber(),
                new Email(entity.getEmail()),
                new GenderId(entity.getGender().getName())
        );
    }

    @Override
    public void updateEntity(Customer data, CustomerEntity entity) {
        var gender = this.genderMapper.findReference(entity.getGender().getId());
        entity.setGivenName(data.givenName());
        entity.setFamilyName(data.familyName());
        entity.setPhoneNumber(data.phoneNumber());
        entity.setEmail(data.email().value());
        entity.setGender(gender);
    }
}
