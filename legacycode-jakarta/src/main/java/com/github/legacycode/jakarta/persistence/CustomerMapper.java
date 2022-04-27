package com.github.legacycode.jakarta.persistence;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.Email;
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
        return email.getValue();
    }

    default Email map(String value) {
        return new Email(value);
    }
}
