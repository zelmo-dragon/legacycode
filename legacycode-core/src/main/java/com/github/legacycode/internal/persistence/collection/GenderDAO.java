package com.github.legacycode.internal.persistence.collection;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.Name;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    public void remove(Name key) {

        DB
                .getInstance()
                .removeIf(Gender.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public boolean contains(Name key) {
        return DB
                .getInstance()
                .contains(Gender.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public Optional<Gender> find(Name key) {
        return DB
                .getInstance()
                .get(Gender.class, e -> Objects.equals(e.getBusinessKey(), key));
    }

}
