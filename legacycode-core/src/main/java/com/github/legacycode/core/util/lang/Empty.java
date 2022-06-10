package com.github.legacycode.core.util.lang;

import java.util.UUID;

public final class Empty {

    public static final UUID EMPTY_UUID = new UUID(0L, 0L);

    private Empty() {
        throw new UnsupportedOperationException("Instance not allowed");
    }
}
