package com.github.legacycode.endpoint;

import java.io.StringWriter;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.JsonbBuilder;

public final class Jsons {

    private Jsons() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static <T> T parse(final Class<T> type, final String document) {

        var builder = JsonbBuilder.create();
        try (builder) {
            return builder.fromJson(document, type);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static <T> T parse(final Class<T> type, final JsonObject document) {

        try (var stringWriter = new StringWriter();
             var writer = Json.createWriter(stringWriter)) {

            writer.writeObject(document);
            var text = stringWriter.toString();
            return parse(type, text);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
