package com.github.legacycode.internal.persistence.collection;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.Email;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    public void remove(UUID key) {

        DB
                .getInstance()
                .removeIf(Customer.class, e -> Objects.equals(e.getKey(), key));
    }

    @Override
    public boolean contains(UUID key) {
        return DB
                .getInstance()
                .contains(Customer.class, e -> Objects.equals(e.getKey(), key));
    }

    @Override
    public Optional<Customer> find(UUID key) {
        return DB
                .getInstance()
                .get(Customer.class, e -> Objects.equals(e.getKey(), key));
    }

}
