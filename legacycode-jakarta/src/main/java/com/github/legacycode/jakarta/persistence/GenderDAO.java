package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.Name;

@Dependent
public class GenderDAO extends AbstractDAO<Gender, Name> implements GenderRepository, Serializable {

    @Inject
    public GenderDAO(EntityManager em) {
        super(em);
    }

    Optional<GenderEntity> findByName(Name name) {

        PredicateBuilder<GenderEntity, GenderEntity> predicate = (c, r, q) -> {
            return null; //c.equal(r.get(GenderEntity_.name), name.value());
        };

        return this.createQuery(GenderEntity.class, predicate).getResultStream().findFirst();

    }

}
