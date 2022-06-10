package com.github.legacycode.core.util.lang;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Either<L, R> {

    private final L left;

    private final R right;

    private Either(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        String text;
        if (isLeft()) {
            text = String.format("Left[%s]", left);
        } else if (isRight()) {
            text = String.format("Right[%s]", right);
        } else {
            text = "Either[null]";
        }
        return text;
    }

    public static <L, R> Either<L, R> left(final L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(final R value) {
        return new Either<>(null, value);
    }

    public static <T, R> Function<T, Either<?, R>> lift(final CheckedFunction<T, R> function) {
        return t -> {
            Either<?, R> result;
            try {
                result = Either.right(function.apply(t));
            } catch (Exception ex) {
                result = Either.left(ex);
            }
            return result;
        };
    }

    public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
        Optional<T> option;
        if (isLeft()) {
            option = Optional.of(mapper.apply(left));
        } else {
            option = Optional.empty();
        }
        return option;
    }

    public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
        Optional<T> option;
        if (isRight()) {
            option = Optional.of(mapper.apply(right));
        } else {
            option = Optional.empty();
        }
        return option;
    }

    public <T> T map(
            final Function<? super L, T> mapperLeft,
            final Function<? super R, T> mapperRight) {

        return getLeft()
                .map(mapperLeft)
                .orElseGet(() -> getRight().map(mapperRight).get());
    }

    public void apply(
            final Consumer<? super L> consumerLeft,
            final Consumer<? super R> consumerRight) {

        getLeft().ifPresent(consumerLeft);
        getRight().ifPresent(consumerRight);
    }

    public boolean isLeft() {
        return Objects.nonNull(left);
    }

    public boolean isRight() {
        return Objects.nonNull(right);
    }

    public Optional<L> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<R> getRight() {
        return Optional.ofNullable(right);
    }

}
