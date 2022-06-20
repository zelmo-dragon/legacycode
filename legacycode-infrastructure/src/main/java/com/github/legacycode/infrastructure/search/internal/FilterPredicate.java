package com.github.legacycode.infrastructure.search.internal;

import java.util.Objects;
import java.util.stream.Stream;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;

import com.github.legacycode.core.util.lang.Strings;

@FunctionalInterface
interface FilterPredicate<X> {

    Predicate toPredicate(CriteriaBuilder builder, Root<X> root, FilterQuery query);

    static <X, V extends Comparable<V>> Predicate equal(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(type, q).stream())
                .map(v -> builder.equal(attribute, v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X, V extends Comparable<V>> Predicate notEqual(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        return FilterPredicate.equal(builder, root, query).not();
    }

    static <X> Predicate like(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<String>get(query.getName());

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(String.class, q).stream())
                .map(v -> String.valueOf(v).toLowerCase())
                .map(Strings::stripAccent)
                .map(v -> "%" + v + "%")
                .map(v -> builder.like(builder.lower(attribute), v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X> Predicate notLike(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        return FilterPredicate.like(builder, root, query).not();
    }

    static <X, V extends Comparable<V>> Predicate greaterThan(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(type, q).stream())
                .map(v -> builder.greaterThan(attribute, v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X, V extends Comparable<V>> Predicate greaterThanOrEqual(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(type, q).stream())
                .map(v -> builder.greaterThanOrEqualTo(attribute, v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X, V extends Comparable<V>> Predicate lessThan(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(type, q).stream())
                .map(v -> builder.lessThan(attribute, v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X, V extends Comparable<V>> Predicate lessThanOrEqual(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return Stream.of(query)
                .flatMap(q -> Queries.asValues(type, q).stream())
                .map(v -> builder.lessThanOrEqualTo(attribute, v))
                .reduce(builder::or)
                .orElseGet(() -> builder.isNull(attribute));
    }

    static <X, V extends Comparable<V>> Predicate in(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        return attribute.in(query.getValues());
    }

    static <X, V extends Comparable<V>> Predicate notIn(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        return FilterPredicate.in(builder, root, query).not();
    }

    static <X, V extends Comparable<V>> Predicate between(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        var attribute = root.<V>get(query.getName());
        var type = attribute.getModel().getBindableJavaType();

        return builder.between(
                attribute,
                Queries.asValue(type, query.getBetweenFirstValue()),
                Queries.asValue(type, query.getBetweenSecondValue())
        );
    }

    static <X, V extends Comparable<V>> Predicate notBetween(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        return FilterPredicate.between(builder, root, query).not();
    }

    static <X> Predicate keyword(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        return root
                .getModel()
                .getAttributes()
                .stream()
                .filter(a -> Objects.equals(a.getPersistentAttributeType(), Attribute.PersistentAttributeType.BASIC))
                .filter(a -> Objects.equals(a.getJavaType(), String.class))
                .map(a -> root.<String>get(a.getName()))
                .map(a -> builder.like(builder.lower(a), Strings.stripAccent(query.getSingleValue())))
                .reduce(builder::or)
                .orElseGet(builder::and);
    }

}
