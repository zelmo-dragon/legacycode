package com.github.legacycode.common.lang;

import java.text.Normalizer;

public final class Strings {

    private Strings() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static String stripAccent(final String word) {
        return Normalizer
                .normalize(word, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "_")
                .toLowerCase();
    }
}
