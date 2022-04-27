package com.github.legacycode.jakarta;


import jakarta.enterprise.inject.spi.CDI;

import com.github.legacycode.core.LegacyCode;

public final class JakartaContext {

    private JakartaContext() {
    }

    public static void applyContext() {
        LegacyCode
                .getCurrentContext()
                .setResolver(p -> CDI.current().select(p).get());
    }
}
