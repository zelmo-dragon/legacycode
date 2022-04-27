package com.github.legacycode.jakarta.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceResource {

    @Produces
    @PersistenceContext
    private EntityManager em;

    public PersistenceResource() {
    }

}
