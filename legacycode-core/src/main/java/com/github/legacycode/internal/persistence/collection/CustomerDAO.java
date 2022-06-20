package com.github.legacycode.internal.persistence.collection;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.repository.EntityEntry;

@Collection
public final class CustomerDAO implements CustomerRepository {

    public CustomerDAO() {
    }

    @Override
    public int size() {
        return DB
                .getInstance()
                .get(Customer.class)
                .size();
    }

    @Override
    public boolean isEmpty() {
        return DB
                .getInstance()
                .get(Customer.class)
                .isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return DB
                .getInstance()
                .contains(Customer.class, e -> Objects.equals(e.getId(), key));
    }

    @Override
    public boolean containsValue(final Object value) {
        return DB
                .getInstance()
                .get(Customer.class)
                .contains(value);
    }

    @Override
    public Customer get(final Object key) {
        return DB
                .getInstance()
                .get(Customer.class, e -> Objects.equals(e.getId(), key))
                .orElse(null);
    }

    @Override
    public Customer put(final UUID key, final Customer value) {
        return DB
                .getInstance()
                .put(Customer.class, Set.of(value));

    }

    @Override
    public Customer remove(final Object key) {
        var entity = this.get(key);
        DB
                .getInstance()
                .remove(Customer.class, entity);

        return entity;
    }

    @Override
    public void putAll(final Map<? extends UUID, ? extends Customer> m) {
        var entities = m.values();
        DB.getInstance().put(Customer.class, Set.copyOf(entities));
    }

    @Override
    public void clear() {
        DB
                .getInstance()
                .remove(Customer.class);
    }

    @Override
    public Set<UUID> keySet() {
        return DB
                .getInstance()
                .get(Customer.class)
                .stream()
                .map(Customer::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public java.util.Collection<Customer> values() {
        return DB
                .getInstance()
                .get(Customer.class);
    }

    @Override
    public Set<Entry<UUID, Customer>> entrySet() {
        return DB
                .getInstance()
                .get(Customer.class)
                .stream()
                .map(e -> new EntityEntry<>(e.getId(), e))
                .collect(Collectors.toSet());
    }
}
