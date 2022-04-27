package com.github.legacycode.jakarta.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
interface PredicateBuilder<T, R> {

    Predicate build(CriteriaBuilder builder, Root<T> root, CriteriaQuery<R> query);
}
