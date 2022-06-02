package com.github.legacycode.endpoint;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Singleton;

@Singleton
public final class EntryManager {

    private final Set<DynamicEntry<?, ?, ?, ?>> entries;

    private EntryManager() {
        this.entries = new HashSet<>();
    }

    public <E, D, M extends DynamicMapper<E, D>, S extends DynamicService<E, D>> void register(final DynamicEntry<E, D, M, S> entry) {
        this.entries.add(entry);
    }

    public <E, D, M extends DynamicMapper<E, D>, S extends DynamicService<E, D>> DynamicEntry<E, D, M, S> resolve(final String name) {
        return (DynamicEntry<E, D, M, S>) this.entries
                .stream()
                .filter(d -> Objects.equals(d.name(), name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No entry registered for name : " + name));
    }

    public <E, D, M extends DynamicMapper<E, D>, S extends DynamicService<E, D>> S invokeService(final String name) {

        DynamicEntry<E, D, M, S> entry = this.resolve(name);
        Class<S> service = entry.service();
        return CDI.current().select(service).get();
    }

}
