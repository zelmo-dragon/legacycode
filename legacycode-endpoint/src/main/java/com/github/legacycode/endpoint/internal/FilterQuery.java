package com.github.legacycode.endpoint.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class FilterQuery {

    private static final String ORDER_BY_QUERY = "orderBy";

    private static final String PAGE_SIZE_QUERY = "pageSize";

    private static final String PAGE_NUMBER_QUERY = "pageNumber";

    private static final String DISTINCT_QUERY = "distinct";

    private static final String KEYWORD_QUERY = "keyword";

    private static final String SELECT_QUERY = "select";

    private static final String ORDER_ASC_SYMBOL = "+";

    private static final String ORDER_DESC_SYMBOL = "-";

    private static final int BETWEEN_FIRST_ARGUMENT = 0;

    private static final int BETWEEN_SECOND_ARGUMENT = 1;

    private final String name;

    private final List<String> values;

    private final Operator operator;

    FilterQuery(final String name, final List<String> values, final Operator operator) {
        this.name = name;
        this.values = List.copyOf(values);
        this.operator = operator;
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq;
        if (this == obj) {
            eq = true;
        } else if (obj == null || getClass() != obj.getClass()) {
            eq = false;
        } else {
            var webQuery = (FilterQuery) obj;
            eq = Objects.equals(name, webQuery.name)
                    && Objects.equals(values, webQuery.values)
                    && operator == webQuery.operator;
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values, operator);
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName())
                .append("{name='").append(name).append('\'')
                .append(", values='").append(values).append('\'')
                .append(", operator=").append(operator)
                .append('}').toString();
    }

    // Accesseurs

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return List.copyOf(values);
    }

    public Operator getOperator() {
        return operator;
    }

    boolean isBasicQuery() {
        return Objects.equals(this.operator, Operator.NONE);
    }

    boolean isSortQuery() {
        return this.isBasicQuery() && Objects.equals(this.name, ORDER_BY_QUERY);
    }

    boolean isPageNumberQuery() {
        return this.isBasicQuery() && Objects.equals(this.name, PAGE_NUMBER_QUERY);
    }

    boolean isPageSizeQuery() {
        return this.isBasicQuery() && Objects.equals(this.name, PAGE_SIZE_QUERY);
    }

    boolean isQueryDistinct() {
        return this.isBasicQuery() && Objects.equals(this.name, DISTINCT_QUERY);
    }

    boolean isKeywordQuery() {
        return this.isBasicQuery() && Objects.equals(this.name, KEYWORD_QUERY);
    }

    boolean isSelectQuery() {
        return this.isBasicQuery() && Objects.equals(this.name, SELECT_QUERY);
    }

    String getBetweenFirstValue() {
        return this.values.get(BETWEEN_FIRST_ARGUMENT);
    }

    String getBetweenSecondValue() {
        return this.values.get(BETWEEN_SECOND_ARGUMENT);
    }

    Map<String, Boolean> getSortedValues() {
        var orders = new HashMap<String, Boolean>();
        for (var v : this.values) {
            var name = String.valueOf(v);
            if (name.startsWith(ORDER_DESC_SYMBOL)) {
                name = name.replace(ORDER_DESC_SYMBOL, "");
                orders.put(name, Boolean.FALSE);
            } else if (name.startsWith(ORDER_ASC_SYMBOL)) {
                name = name.replace(ORDER_ASC_SYMBOL, "");
                orders.put(name, Boolean.TRUE);
            } else {
                orders.put(name, Boolean.TRUE);
            }
        }
        return orders;
    }

    String getSingleValue() {
        String value;
        if (!this.values.isEmpty()) {
            value = this.values.get(0);
        } else {
            value = null;
        }
        return value;
    }

}
