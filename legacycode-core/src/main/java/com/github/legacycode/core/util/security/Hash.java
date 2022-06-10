package com.github.legacycode.core.util.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Hash {

    public static final String MD5 = "MD5";

    public static final String SHA1 = "SHA-1";

    public static final String SHA224 = "SHA-224";

    public static final String SHA256 = "SHA-256";

    public static final String SHA384 = "SHA-384";

    public static final String SHA512 = "SHA-512";

    private Hash() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static String execute(final String algorithm, final String word) {
        final StringBuilder hexaCode = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(word.getBytes(StandardCharsets.UTF_8));
            final byte[] bytes = md.digest();
            for (byte b : bytes) {
                final String hexa = Integer.toHexString(0xFF & b);
                if (hexa.length() == 1) {
                    hexaCode.append('0');
                }
                hexaCode.append(hexa);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return hexaCode.toString();
    }
}
