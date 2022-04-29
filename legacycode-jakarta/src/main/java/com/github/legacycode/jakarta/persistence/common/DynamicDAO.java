package com.github.legacycode.jakarta.persistence.common;

import java.io.Serializable;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import com.github.legacycode.jakarta.persistence.Entity;

@Dependent
public class DynamicDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final transient EntityManager em;

    @Inject
    public DynamicDAO(EntityManager em) {
        this.em = em;
    }

    public <E extends Entity<K>, K> E add(E entity) {
        E attachedEntity;
        if (this.em.contains(entity)) {
            attachedEntity = this.em.merge(entity);
        } else {
            this.em.persist(entity);
            attachedEntity = entity;
        }
        return attachedEntity;
    }

    public <E extends Entity<K>, K> void remove(Class<E> entityClass, E entity) {
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        var id = puu.getIdentifier(entity);
        var attachedEntity = this.em.getReference(entityClass, id);
        this.em.remove(attachedEntity);
    }

    public <E extends Entity<K>, K> boolean contains(E entity) {
        var entityClass = (Class<E>) entity.getClass();
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(entityClass);
        cq.select(cb.count(root));
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        var id = puu.getIdentifier(entity);
        var attribut = getPrimaryKeyAttribut(entityClass);
        var p0 = cb.equal(root.get(attribut), id);
        cq.where(p0);
        return this.em.createQuery(cq).getSingleResult() > 0;
    }

    public <E extends Entity<K>, K> Optional<E> find(Class<E> entityClass, K key) {
        var attachedEntity = this.em.find(entityClass, key);
        return Optional.ofNullable(attachedEntity);
    }

    private <E extends Entity<K>, K> SingularAttribute<E, ?> getPrimaryKeyAttribut(Class<E> entityClass) {
        return this.em
                .getMetamodel()
                .entity(entityClass)
                .getDeclaredSingularAttributes()
                .stream()
                .filter(SingularAttribute::isId)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("No primary key attribut found in class: " + entityClass));

    }

}
