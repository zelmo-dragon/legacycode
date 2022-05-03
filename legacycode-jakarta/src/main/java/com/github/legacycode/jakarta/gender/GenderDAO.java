package com.github.legacycode.jakarta.gender;

import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.legacycode.jakarta.persistence.AbstractDAO;

@Dependent
public class GenderDAO extends AbstractDAO<GenderEntity, UUID> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    public GenderDAO(EntityManager em) {
        super(em);
    }


}
