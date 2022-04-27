package com.github.legacycode.jakarta.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceResource {

    @Produces
    @PersistenceContext
    private EntityManager em;

    public PersistenceResource() {
    }

}
