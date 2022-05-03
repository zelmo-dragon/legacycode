package com.github.legacycode.jakarta.gender;

import java.io.Serializable;
import java.util.UUID;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import com.github.legacycode.jakarta.persistence.AbstractDAO;

@Dependent
public class GenderDAO extends AbstractDAO<GenderEntity, UUID> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    public GenderDAO(EntityManager em) {
        super(em);
    }


}
