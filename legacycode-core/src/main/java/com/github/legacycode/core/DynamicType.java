package com.github.legacycode.core;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.CustomerService;
import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.GenderService;
import com.github.legacycode.core.repository.Identifiable;
import com.github.legacycode.core.repository.Repository;

public enum DynamicType {

    CUSTOMER("customer", Customer.class, CustomerRepository.class, CustomerService.class),
    GENDER("gender", Gender.class, GenderRepository.class, GenderService.class);

    private final String canonicalName;

    private final Class<? extends Identifiable<?>> entityClass;

    private final Class<? extends Repository<?, ?>> repositoryClass;

    private final Class<?> serviceClass;

    DynamicType(
            final String canonicalName,
            final Class<? extends Identifiable<?>> entityClass,
            final Class<? extends Repository<?, ?>> repositoryClass,
            final Class<?> serviceClass) {

        this.canonicalName = canonicalName;
        this.entityClass = entityClass;
        this.repositoryClass = repositoryClass;
        this.serviceClass = serviceClass;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public <E extends Identifiable<K>, K> Class<E> getEntityClass() {
        return (Class<E>) entityClass;
    }

    public Class<? extends Repository<?, ?>> getRepositoryClass() {
        return repositoryClass;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

}
