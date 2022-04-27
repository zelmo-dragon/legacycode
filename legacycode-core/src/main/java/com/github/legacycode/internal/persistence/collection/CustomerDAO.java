package com.github.legacycode.internal.persistence.collection;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.Email;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Collection
public final class CustomerDAO implements CustomerRepository {

    public CustomerDAO() {
    }

    @Override
    public void add(Customer entity) {

        DB
                .getInstance()
                .put(Customer.class, Set.of(entity));
    }

    @Override
    public void remove(Email key) {

        DB
                .getInstance()
                .removeIf(Customer.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public boolean contains(Email key) {
        return DB
                .getInstance()
                .contains(Customer.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public Optional<Customer> find(Email key) {
        return DB
                .getInstance()
                .get(Customer.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

}
