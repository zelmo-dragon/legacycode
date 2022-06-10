package com.github.legacycode.core.util.validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Constraint {

    public static final String MESSAGE_NOT_NULL = "can not be null";

    public static final String MESSAGE_NOT_EMPTY = "can not be null or empty";

    public static final String MESSAGE_NOT_EMPTY_OBJECT = "can not be an empty object";

    public static final String MESSAGE_NOT_CONTAIN_EMPTY_OBJECT = "can not contain an empty object";

    public static final String MESSAGE_NOT_EQUALS = "can not be equals";

    public static final String MESSAGE_GREATER_THAN_ZERO = "must be greater than zero";

    public static final String MESSAGE_GREATER_THAN_OR_EQUAL_ZERO = "must be greater than or equal to zero";

    public static final String MESSAGE_INVALID_EMAIL = "invalid email format";

    public static final String MESSAGE_DATE_NOT_IN_PAST = "date is not in past";

    public static final String MESSAGE_DATE_NOT_IN_FUTURE = "date is not in future";

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);

    private Constraint() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static boolean greaterThanZero(final Integer number) {
        return number > 0;
    }

    public static boolean greaterThanZero(final BigDecimal number) {
        return number.signum() > 0;
    }

    public static boolean greaterThanOrEqualZero(final Integer number) {
        return number >= 0;
    }

    public static boolean greaterThanOrEqualZero(final BigDecimal number) {
        return number.signum() >= 0;
    }

    public static boolean notEmpty(final String text) {
        return Objects.nonNull(text) && !text.trim().isEmpty();
    }

    public static boolean notEmpty(final Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    public static boolean notContains(final Collection<?> collection, final Object obj) {
        return !collection.contains(obj);
    }

    public static boolean notEquals(final Object a, final Object b) {
        return !Objects.equals(a, b);
    }

    public static boolean isInPast(final LocalDateTime date) {
        return LocalDateTime.now().isAfter(date);
    }

    public static boolean isInFuture(final LocalDateTime date) {
        return LocalDateTime.now().isBefore(date);
    }

    public static boolean isEmailValid(final String text) {
        return EMAIL_REGEX.matcher(text).matches();
    }
}
