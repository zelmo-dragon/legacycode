package com.github.legacycode.core.util.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class HashMac {

    public static final String HMAC_MD5 = "HmacMD5";

    public static final String HMAC_SHA1 = "HmacSHA1";

    public static final String HMAC_SHA256 = "HmacSHA256";

    private HashMac() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static String execute(final String algorithm, final String key, final String word) {

        final SecretKey secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        final StringBuilder hexaCode = new StringBuilder();
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secret);
            final byte[] bytes = mac.doFinal(word.getBytes(StandardCharsets.UTF_8));
            for (byte b : bytes) {
                final String hexa = Integer.toHexString(0xFF & b);
                if (hexa.length() == 1) {
                    hexaCode.append('0');
                }
                hexaCode.append(hexa);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new IllegalArgumentException(ex);
        }
        return hexaCode.toString();
    }
}
