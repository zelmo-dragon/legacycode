package com.github.legacycode.jakarta.persistence;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class PersistenceResource {

    @Produces
    @PersistenceContext
    private EntityManager em;

    public PersistenceResource() {
    }

}
