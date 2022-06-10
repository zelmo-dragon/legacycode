package com.github.legacycode.jakarta.customer;

import java.util.UUID;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import com.github.legacycode.repository.AbstractMapDAO;

@Singleton
public class CustomerDAO extends AbstractMapDAO<UUID, CustomerEntity> {

    @Inject
    public CustomerDAO(EntityManager em) {
        super(em);
    }

}
