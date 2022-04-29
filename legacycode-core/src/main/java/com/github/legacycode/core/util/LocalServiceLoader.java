package com.github.legacycode.core.util;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import com.github.legacycode.core.LegacyCodeException;

public final class LocalServiceLoader {

    private LocalServiceLoader() {
    }

    public static <S> S loadProvider(
            final Class<S> providerClass,
            final Class<? extends Annotation> annotation) {

        return ServiceLoader
                .load(providerClass)
                .stream()
                .filter(p -> p.type().isAnnotationPresent(annotation))
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow(() -> LegacyCodeException.PROVIDER_NOT_FOUND.apply(providerClass));
    }

    public static <S> S loadProvider(final Class<S> providerClass) {

        return ServiceLoader
                .load(providerClass)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow(() -> LegacyCodeException.PROVIDER_NOT_FOUND.apply(providerClass));
    }
}
