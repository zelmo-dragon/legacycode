package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class GenderDAO extends AbstractDAO<GenderEntity, UUID> implements Serializable {

    @Inject
    public GenderDAO(EntityManager em) {
        super(em);
    }


}
