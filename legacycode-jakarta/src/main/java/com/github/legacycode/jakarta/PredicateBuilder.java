package com.github.legacycode.jakarta;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
interface PredicateBuilder<T, R> {

    Predicate build(CriteriaBuilder builder, Root<T> root, CriteriaQuery<R> query);
}
