package com.github.legacycode.infrastructure.search.internal;

import java.util.Objects;
import java.util.stream.Stream;

enum Operator {

    EQUAL("eq"),

    NOT_EQUAL("neq"),

    LIKE("li"),

    NOT_LIKE("nli"),

    GREATER_THAN("gt"),

    GREATER_THAN_OR_EQUAL("ge"),

    LESS_THAN("lt"),

    LESS_THAN_OR_EQUAL("le"),

    IN("in"),

    NOT_IN("nin"),

    BETWEEN("bt"),

    NOT_BETWEEN("nbt"),

    NONE("");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    static Operator parse(final String symbol) {
        return Stream
                .of(values())
                .filter(e -> Objects.equals(e.symbol, symbol))
                .findFirst()
                .orElse(Operator.NONE);
    }

    static boolean isNone(final String symbol) {
        return Objects.isNull(symbol) || Objects.equals(NONE.symbol, symbol);
    }
}
