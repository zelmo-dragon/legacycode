package com.github.legacycode.jakarta.persistence;

import java.util.Optional;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.Email;
import com.github.legacycode.core.customer.GenderId;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", uses = GenderMapper.class)
public interface CustomerMapper extends EntityMapper<CustomerEntity, Customer> {

    @Override
    CustomerEntity toEntity(Customer data);

    @InheritInverseConfiguration(name = "toEntity")
    @Override
    Customer fromEntity(CustomerEntity entity);

    @Override
    void updateEntity(Customer data, @MappingTarget CustomerEntity entity);

    default String map(Email email) {
        return Optional
                .ofNullable(email)
                .map(Email::getValue)
                .orElse(null);
    }

    default Email map(String value) {
        return new Email(value);
    }

    default GenderEntity map(GenderId value) {

        return null;
    }
}
