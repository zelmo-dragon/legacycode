package com.github.legacycode.jakarta.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Queries {

    private static final int DEFAULT_PAGE_NUMBER = 1;

    private static final int DEFAULT_PAGE_SIZE = 100;

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = Map.of(
            Boolean.class, Boolean::parseBoolean,
            Byte.class, Byte::parseByte,
            Short.class, Short::parseShort,
            Integer.class, Integer::parseInt,
            Long.class, Long::parseLong,
            Float.class, Float::parseFloat,
            Double.class, Double::parseDouble,
            String.class, String::valueOf
    );

    private Queries() {
    }

    static <T> T asValue(final Class<T> type, final String rawValue) {
        return (T) CONVERTERS.get(type).apply(rawValue);
    }

    static <T> T asValue(final Class<T> type, final DynamicQuery query) {
        return (T) CONVERTERS.get(type).apply(query.getSingleValue());
    }

    static <T> List<T> asValues(final Class<T> type, final DynamicQuery query) {

        return query.getValues()
                .stream()
                .map(s -> (T) CONVERTERS.get(type).apply(s))
                .collect(Collectors.toList());
    }

    static boolean isDistinct(final Set<DynamicQuery> queries) {
        return queries
                .stream()
                .filter(DynamicQuery::isQueryDistinct)
                .map(q -> asValue(Boolean.class, q))
                .findFirst()
                .orElse(Boolean.FALSE);
    }

    static int getPageNumber(final Set<DynamicQuery> queries) {
        return queries
                .stream()
                .filter(DynamicQuery::isPageNumberQuery)
                .map(q -> asValue(Integer.class, q))
                .findFirst()
                .orElse(DEFAULT_PAGE_NUMBER);
    }

    static int getPageSize(final Set<DynamicQuery> queries) {
        return queries
                .stream()
                .filter(DynamicQuery::isPageSizeQuery)
                .map(q -> asValue(Integer.class, q))
                .findFirst()
                .orElse(DEFAULT_PAGE_SIZE);
    }

    static Set<DynamicQuery> extractParameters(final Map<String, List<String>> parameters) {

        return parameters
                .entrySet()
                .stream()
                .map(e -> new DynamicQuery(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }

}
