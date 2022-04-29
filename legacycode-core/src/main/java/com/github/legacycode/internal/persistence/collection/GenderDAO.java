package com.github.legacycode.internal.persistence.collection;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;

@Collection
public final class GenderDAO implements GenderRepository {

    public GenderDAO() {
    }

    @Override
    public void add(Gender entity) {

        DB
                .getInstance()
                .put(Gender.class, Set.of(entity));

    }

    @Override
    public void remove(UUID key) {

        DB
                .getInstance()
                .removeIf(Gender.class, e -> Objects.equals(e.getKey(), key));
    }

    @Override
    public boolean contains(UUID key) {
        return DB
                .getInstance()
                .contains(Gender.class, e -> Objects.equals(e.getKey(), key));
    }

    @Override
    public Optional<Gender> find(UUID key) {
        return DB
                .getInstance()
                .get(Gender.class, e -> Objects.equals(e.getKey(), key));
    }

}
