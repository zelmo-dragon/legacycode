package com.github.legacycode.common.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordPolicy {

    public static final String PBKDF2_HMAC_SHA224 = "PBKDF2WithHmacSHA224";

    public static final String PBKDF2_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

    public static final String PBKDF2_HMAC_SHA384 = "PBKDF2WithHmacSHA384";

    public static final String PBKDF2_HMAC_SHA512 = "PBKDF2WithHmacSHA512";

    public static final int DEFAULT_ITERATIONS = 2048;

    public static final int DEFAULT_SALT_SIZE_BYTES = 32;

    public static final int DEFAULT_KEY_SIZE_BYTES = 32;

    private static final String SEPARATOR = ":";

    private static final int ALGORITHM_INDEX = 0;

    private static final int ITERATION_INDEX = 1;

    private static final int SALT_INDEX = 2;

    private static final int PASSWORD_HASH_INDEX = 3;

    private static final int DATA_LENGTH = 4;

    private PasswordPolicy() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static String generate(final String password) {
        return generate(password,
                PBKDF2_HMAC_SHA256,
                DEFAULT_ITERATIONS,
                DEFAULT_SALT_SIZE_BYTES,
                DEFAULT_KEY_SIZE_BYTES
        );
    }

    public static String generate(
            final String password,
            final String algorithm,
            final int iterations,
            final int saltSizeBytes,
            final int keysizeBytes) {

        String data;
        try {
            // Génération du sel aléatoirement.
            var random = new SecureRandom();
            var salt = new byte[saltSizeBytes];
            random.nextBytes(salt);

            // Hachage du mot de passe.
            var spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keysizeBytes);
            var factory = SecretKeyFactory.getInstance(algorithm);
            var passwordHash = factory.generateSecret(spec).getEncoded();

            // Encodage en Base64 du mot de passe et du sel.
            var base64Salt = Base64.getEncoder().encode(salt);
            var base64Password = Base64.getEncoder().encode(passwordHash);

            // Génération du résultat encodé au format:
            // <algorithm>:<iterations>:<base64(salt)>:<base64(hash)>
            data = String.join(SEPARATOR,
                    algorithm,
                    String.valueOf(iterations),
                    new String(base64Salt, StandardCharsets.UTF_8),
                    new String(base64Password, StandardCharsets.UTF_8)
            );

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return data;
    }

    public static boolean verify(final String password, final String passwordEncoded) {
        boolean equals;
        try {
            var data = passwordEncoded.split(SEPARATOR);
            if (data.length != DATA_LENGTH) {
                throw new IllegalArgumentException("Wrong password format, expected: \"<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>\"");
            }
            // Récupération des paramètres
            var algorithm = data[ALGORITHM_INDEX];
            var iterations = Integer.valueOf(data[ITERATION_INDEX]);
            var salt = Base64.getDecoder().decode(data[SALT_INDEX]);
            var passwordHash = Base64.getDecoder().decode(data[PASSWORD_HASH_INDEX]);
            var keySizeByte = passwordHash.length * 8;

            // Calcul du hash avec le mot de passe initial.
            var spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keySizeByte);
            var factory = SecretKeyFactory.getInstance(algorithm);
            var currentPasswordHash = factory.generateSecret(spec).getEncoded();

            // Comparaison des deux hashs.
            equals = Arrays.equals(
                    currentPasswordHash,
                    passwordHash
            );

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return equals;
    }

}
