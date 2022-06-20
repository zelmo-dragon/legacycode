package com.github.legacycode.sample.gender;

import java.util.UUID;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import com.github.legacycode.infrastructure.persistence.AbstractMapDAO;

@Singleton
public class GenderDAO extends AbstractMapDAO<UUID, GenderEntity> {

    @Inject
    public GenderDAO(EntityManager em) {
        super(em);
    }


}
