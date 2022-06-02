package com.github.legacycode.endpoint;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface CriteriaPredicate<E, R> {

    Predicate toPredicate(CriteriaBuilder builder, Root<E> root, CriteriaQuery<R> query);
}
