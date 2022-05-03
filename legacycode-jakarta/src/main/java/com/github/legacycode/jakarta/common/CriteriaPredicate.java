package com.github.legacycode.jakarta.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface CriteriaPredicate<E, R> {

    Predicate toPredicate(CriteriaBuilder builder, Root<E> root, CriteriaQuery<R> query);
}
