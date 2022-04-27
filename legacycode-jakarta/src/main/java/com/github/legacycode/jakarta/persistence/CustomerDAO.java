package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.Email;

@Dependent
public class CustomerDAO extends AbstractDAO<Customer, Email> implements CustomerRepository, Serializable {

    @Inject
    public CustomerDAO(EntityManager em) {
        super(em);
    }

}
