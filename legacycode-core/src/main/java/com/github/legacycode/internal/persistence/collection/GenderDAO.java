package com.github.legacycode.internal.persistence.collection;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.repository.EntityEntry;

@Collection
public final class GenderDAO implements GenderRepository {

    public GenderDAO() {
    }

    @Override
    public int size() {
        return DB
                .getInstance()
                .get(Gender.class)
                .size();
    }

    @Override
    public boolean isEmpty() {
        return DB
                .getInstance()
                .get(Gender.class)
                .isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return DB
                .getInstance()
                .contains(Gender.class, e -> Objects.equals(e.getId(), key));
    }

    @Override
    public boolean containsValue(final Object value) {
        return DB
                .getInstance()
                .get(Gender.class)
                .contains(value);
    }

    @Override
    public Gender get(final Object key) {
        return DB
                .getInstance()
                .get(Gender.class, e -> Objects.equals(e.getId(), key))
                .orElse(null);
    }

    @Override
    public Gender put(final UUID key, final Gender value) {
        return DB
                .getInstance()
                .put(Gender.class, Set.of(value));

    }

    @Override
    public Gender remove(final Object key) {
        var entity = this.get(key);
        DB
                .getInstance()
                .remove(Gender.class, entity);

        return entity;
    }

    @Override
    public void putAll(final Map<? extends UUID, ? extends Gender> m) {
        var entities = m.values();
        DB.getInstance().put(Gender.class, Set.copyOf(entities));
    }

    @Override
    public void clear() {
        DB
                .getInstance()
                .remove(Gender.class);
    }

    @Override
    public Set<UUID> keySet() {
        return DB
                .getInstance()
                .get(Gender.class)
                .stream()
                .map(Gender::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public java.util.Collection<Gender> values() {
        return DB
                .getInstance()
                .get(Gender.class);
    }

    @Override
    public Set<Entry<UUID, Gender>> entrySet() {
        return DB
                .getInstance()
                .get(Gender.class)
                .stream()
                .map(e -> new EntityEntry<>(e.getId(), e))
                .collect(Collectors.toSet());
    }
}
